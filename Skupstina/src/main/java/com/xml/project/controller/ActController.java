package com.xml.project.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentDescriptor;
import com.marklogic.client.document.DocumentUriTemplate;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.DocumentMetadataHandle.DocumentCollections;
import com.marklogic.client.io.InputStreamHandle;
import com.xml.project.jaxb.Dokument;
import com.xml.project.service.UserService;
import com.xml.project.util.DatabaseUtil;

@RestController
@RequestMapping(value = "api/act")
public class ActController {

	@Autowired
	UserService userService;

	private DatabaseClient databaseClient;
	private DatabaseUtil dUtil = new DatabaseUtil();
	private Marshaller m;
	private JAXBContext context;
	XMLDocumentManager xmlMenager;
	Unmarshaller unmarshaller;
	String XML_FILE = "data/";

	/**
	 * Add new act
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> saveAct(@RequestBody Dokument doc)
			throws JAXBException, IOException, SAXException {
		
		//connecti to marklogic
		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(), dUtil.getDatabase(),
				dUtil.getUsername(), dUtil.getPassword(), dUtil.getAuthType());
		xmlMenager = databaseClient.newXMLDocumentManager();

		context = JAXBContext.newInstance("com.xml.project.jaxb");

		m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		unmarshaller = context.createUnmarshaller();
		
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File(XML_FILE+"skupstina.xsd"));
		
		
		m.setSchema(schema);
		m.setEventHandler(new MyValidationEventHandler());
		
		unmarshaller.setSchema(schema);
		unmarshaller.setEventHandler(new MyValidationEventHandler());
		
		File f = new File(XML_FILE+"/act.xml");
		FileOutputStream out = new FileOutputStream(f);
		m.marshal(doc, out);
		
		xmlMenager = databaseClient.newXMLDocumentManager();
		DocumentUriTemplate template = xmlMenager.newDocumentUriTemplate("xml");
		template.setDirectory("/acts/decisions/");
		
		InputStreamHandle handle = new InputStreamHandle(new FileInputStream(XML_FILE+"act.xml"));
		
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add("/parliament/acts/proposed");
		
		DocumentDescriptor desc = xmlMenager.create(template, metadata, handle);
		
		databaseClient.release();
		
		return new ResponseEntity<String>("Dokument je snimljen u bazu.", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/accept/{docId}", method = RequestMethod.GET)
	public ResponseEntity<String> acceptAct(@PathVariable String docId)
			throws JAXBException, IOException, SAXException {
		
		//connecti to marklogic
		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(), dUtil.getDatabase(),
				dUtil.getUsername(), dUtil.getPassword(), dUtil.getAuthType());
		xmlMenager = databaseClient.newXMLDocumentManager();

		xmlMenager = databaseClient.newXMLDocumentManager();
		
		String doc = "/acts/decisions/"+docId+".xml";
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		xmlMenager.readMetadata(doc, metadata);
		
		DocumentCollections collections = metadata.getCollections();
		collections.remove("/parliament/acts/proposed");
		collections.add("/parliament/acts/accepted");
		xmlMenager.writeMetadata(doc, metadata);
		
		databaseClient.release();
		return new ResponseEntity<String>("Dokument je prihvacen.", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/find/{docId}", method = RequestMethod.GET)
	public ResponseEntity<Dokument> findByIdAct(@PathVariable String docId)
			throws JAXBException, IOException, SAXException {
		
		Dokument dokument = null;
		//connecti to marklogic
		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(), dUtil.getDatabase(),
				dUtil.getUsername(), dUtil.getPassword(), dUtil.getAuthType());
		
		xmlMenager = databaseClient.newXMLDocumentManager();
		DOMHandle content = new DOMHandle();
		
		String doc = "/acts/decisions/"+docId+".xml";
		
		xmlMenager.read(doc, content);
		
		databaseClient.release();
		
		Document docc = content.get();
		
		dokument = (Dokument) unmarshaller.unmarshal(docc);
		unmarshaller.setEventHandler(new MyValidationEventHandler());
		
		return new ResponseEntity<Dokument>(dokument, HttpStatus.OK);
	}
	
}
