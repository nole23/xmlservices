package com.xml.project.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;

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
import org.xml.sax.SAXException;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentDescriptor;
import com.marklogic.client.document.DocumentUriTemplate;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import com.xml.project.jaxb.Amandman;
import com.xml.project.jaxb.Dokument;
import com.xml.project.model.Published;
import com.xml.project.model.User;
import com.xml.project.repository.PublishedRepository;
import com.xml.project.service.UserService;
import com.xml.project.util.DatabaseUtil;

@RestController
@RequestMapping(value = "api/amandman")
public class AmandmanController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PublishedRepository publishedRepository;

	private static DatabaseClient databaseClient;
	private static DatabaseUtil dUtil = new DatabaseUtil();
	private static Marshaller m;
	public static JAXBContext context;
	static XMLDocumentManager xmlMenager;
	static Unmarshaller unmarshaller;
	String XML_FILE = "data/";

	static {
		try {
			databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(), dUtil.getDatabase(),
					dUtil.getUsername(), dUtil.getPassword(), dUtil.getAuthType());

			xmlMenager = databaseClient.newXMLDocumentManager();

			context = JAXBContext.newInstance("com.xml.project.jaxb");

			m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			unmarshaller = context.createUnmarshaller();
		} catch (JAXBException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Add new amandman
	 */
	@RequestMapping(value = "/add/{tip}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> saveAmandman(@RequestBody Amandman amandman, Principal principal, @PathVariable String tip)
			throws JAXBException, IOException, SAXException {

		User user = userService.findByUsername(principal.getName());

		System.out.println(amandman.getNaslovAkta());

		if (user == null)
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File(XML_FILE + "amandma.xsd"));

		m.setSchema(schema);
		m.setEventHandler(new MyValidationEventHandler());

		unmarshaller.setSchema(schema);
		unmarshaller.setEventHandler(new MyValidationEventHandler());

		File f = new File(XML_FILE + "/amandman.xml");
		FileOutputStream out = new FileOutputStream(f);
		m.marshal(amandman, out);

		xmlMenager = databaseClient.newXMLDocumentManager();
		DocumentUriTemplate template = xmlMenager.newDocumentUriTemplate("xml");

		template.setDirectory("/amandman/decisions/");

		InputStreamHandle handle = new InputStreamHandle(new FileInputStream(XML_FILE + "amandman.xml"));
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add("/parliament/amandman/proposed");

		DocumentDescriptor desc = xmlMenager.create(template, metadata, handle);

		
		Published published = new Published();

		published.setXmlLink(desc.getUri());
		published.setAccepted(false);
		published.setUser(user);

		publishedRepository.save(published);
		
		return new ResponseEntity<String>("sacuvao"+desc, HttpStatus.OK);
	}

}
