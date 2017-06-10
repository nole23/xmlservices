package com.xml.project.controller;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DOMHandle;
import com.xml.project.jaxb.Amandman;
import com.xml.project.jaxb.Dokument;
import com.xml.project.util.DatabaseUtil;

public class AcceptAmandman {

	private static DatabaseClient databaseClient;
	private static DatabaseUtil dUtil = new DatabaseUtil();
	private static Marshaller m;
	public static JAXBContext context;
	static XMLDocumentManager xmlMenager;
	static Unmarshaller unmarshaller;
	static String XML_FILE = "data/";
	
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
	
	//Treba implementirati nekako 
	public String Accept(String docId) throws SAXException, JAXBException {
		
		Amandman amandman = null;

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File(XML_FILE + "amandma.xsd"));

		unmarshaller.setSchema(schema);

		DOMHandle content = new DOMHandle();

		String doc = "/amandman/decisions/" + docId + ".xml";

		xmlMenager.read(doc, content);

		Document docc = content.get();
		unmarshaller.setEventHandler(new MyValidationEventHandler());
		amandman = (Amandman) unmarshaller.unmarshal(docc);
		
		//////////////////////////////////////////////////////
		
		Dokument dokument = null;

		SchemaFactory schemaFactory1 = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema1 = schemaFactory1.newSchema(new File(XML_FILE + "skupstina.xsd"));

		unmarshaller.setSchema(schema1);

		DOMHandle content1 = new DOMHandle();

		String doc1 = "/amandman/decisions/" + docId + ".xml";

		xmlMenager.read(doc1, content1);

		Document docc1 = content1.get();
		unmarshaller.setEventHandler(new MyValidationEventHandler());
		amandman = (Amandman) unmarshaller.unmarshal(docc1);
		
		return null;
	}
}
