package com.xml.project.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.itextpdf.text.DocumentException;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentDescriptor;
import com.marklogic.client.document.DocumentUriTemplate;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.DocumentMetadataHandle.DocumentCollections;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import com.xml.project.dto.SearchDTO;
import com.xml.project.jaxb.Dokument;
import com.xml.project.service.UserService;
import com.xml.project.util.DatabaseUtil;
import com.xml.project.util.PDFUtil;



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
	
	/**
	 * Search for name file (xxx.xml)
	 * @param docId
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 * @throws SAXException
	 */
	@RequestMapping(value = "/find/{docId}", method = RequestMethod.GET)
	public ResponseEntity<Dokument> findByIdAct(@PathVariable String docId)
			throws JAXBException, IOException, SAXException {
		
		Dokument dokument = null;
		//connecti to marklogic
		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(), dUtil.getDatabase(),
				dUtil.getUsername(), dUtil.getPassword(), dUtil.getAuthType());
		
		xmlMenager = databaseClient.newXMLDocumentManager();
		
		
		context = JAXBContext.newInstance("com.xml.project.jaxb");
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File(XML_FILE+"skupstina.xsd"));
		unmarshaller = context.createUnmarshaller();
		unmarshaller.setSchema(schema);
		
		DOMHandle content = new DOMHandle();
		
		String doc = "/acts/decisions/"+docId+".xml";

		xmlMenager.read(doc, content);
		databaseClient.release();

		Document docc = content.get();
		unmarshaller.setEventHandler(new MyValidationEventHandler());
		dokument = (Dokument) unmarshaller.unmarshal(docc);

		return new ResponseEntity<Dokument>(dokument, HttpStatus.OK);
	}
	
	/*
	 * Potrebno je proslediti jedan od dva parametra
	 * 1. proposed
	 * 2. accepted
	 */
	@RequestMapping(value = "/collection/{coll}", method = RequestMethod.GET)
	public ResponseEntity<List<SearchDTO>> findByCollection(@PathVariable String coll)
			throws JAXBException, IOException, SAXException {
		
		List<SearchDTO> searchDTO = new ArrayList<SearchDTO>();

		String collId = "/parliament/acts/"+coll;
		
		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(), dUtil.getDatabase(),
				dUtil.getUsername(), dUtil.getPassword(), dUtil.getAuthType());
		
		QueryManager queryManager = databaseClient.newQueryManager();
		StringQueryDefinition queryDefinition = queryManager.newStringDefinition();
		
		queryDefinition.setCollections(collId);
		
		SearchHandle results = queryManager.search(queryDefinition, new SearchHandle());
		MatchDocumentSummary matches[] = results.getMatchResults();
		MatchDocumentSummary result;
		
		for(int i=0; i<matches.length; i++) {
			result = matches[i];
			String title = getDocumentTitle(result.getUri());
			searchDTO.add(new SearchDTO(result.getUri(), title));
		}
		
		databaseClient.release();
		
		
		return new ResponseEntity<List<SearchDTO>>(searchDTO, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/delete/{docId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCollection(@PathVariable String docId)
			throws JAXBException, IOException, SAXException {
		
		String collId = "/acts/decisions/"+docId+".xml";
		
		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(), dUtil.getDatabase(),
				dUtil.getUsername(), dUtil.getPassword(), dUtil.getAuthType());
		
		xmlMenager = databaseClient.newXMLDocumentManager();
		xmlMenager.delete(collId);
		
		databaseClient.release();
		
		return new ResponseEntity<String>("izbrisano", HttpStatus.OK);
	}

	public String getDocumentTitle(String docId) throws JAXBException {
		String title = "";
		Dokument dokument = null;
		// Create a document manager to work with XML files.
		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(), dUtil.getDatabase(),
				dUtil.getUsername(), dUtil.getPassword(), dUtil.getAuthType());
		
		xmlMenager = databaseClient.newXMLDocumentManager();
		DOMHandle content = new DOMHandle();
		
		xmlMenager.read(docId, content);
		
		databaseClient.release();
		
		Document docc = content.get();
		
		dokument = (Dokument) unmarshaller.unmarshal(docc);
		unmarshaller.setEventHandler(new MyValidationEventHandler());
		
		title = dokument.getNaslov();
		
		return title;
	}
	
	@RequestMapping(value="/convert/{docId}/{typeId}", method=RequestMethod.GET)
	public void convert(HttpServletResponse response, @PathVariable String docId, @PathVariable String typeId)
			throws JAXBException, IOException, SAXException, DocumentException{
		
		String doc = "/acts/decisions/"+docId+".xml";
		
		Dokument dokument = null;
		
		// Create a document manager to work with XML files.
		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(), dUtil.getDatabase(),
				dUtil.getUsername(), dUtil.getPassword(), dUtil.getAuthType());
		
		xmlMenager = databaseClient.newXMLDocumentManager();
		
		DOMHandle content = new DOMHandle();
		
		xmlMenager.read(doc, content);
		
		databaseClient.release();
		
		Document docc = content.get();
		
		
		context = JAXBContext.newInstance("com.xml.project.jaxb");
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File(XML_FILE+"skupstina.xsd"));
		unmarshaller = context.createUnmarshaller();
		unmarshaller.setSchema(schema);
		dokument = (Dokument) unmarshaller.unmarshal(docc);
		
		unmarshaller.setEventHandler(new MyValidationEventHandler());
		
		
		File file = new File(XML_FILE+docId+".xml");
		System.out.println(dokument);
		FileOutputStream out = new FileOutputStream(file);
		
		m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.setSchema(schema);
		m.setEventHandler(new MyValidationEventHandler());
		m.marshal(dokument, out);
		
		PDFUtil pdfUtil = new PDFUtil();

		File outputFile = new File(XML_FILE+docId+".pdf");

		//pdfUtil.generatePDF(outputFile, dokument);
		//get file
		
		String xmlPath = XML_FILE+docId+".xml";
		String xslPath = XML_FILE+"act.xsl";
		String fileOutput = XML_FILE+docId+".html";
		pdfUtil.generateHTML(xmlPath, xslPath, fileOutput);

		File file1 = new File(XML_FILE+docId+".pdf");
		String mimeType= URLConnection.guessContentTypeFromName(file1.getName());
		
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file1.getName() +"\""));
		response.setContentLength((int)file1.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file1));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		
		
		
		
	}
	
}
