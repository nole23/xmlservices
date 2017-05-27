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
import com.xml.project.jaxb.Clan;
import com.xml.project.jaxb.Dokument;
import com.xml.project.model.User;
import com.xml.project.service.UserService;
import com.xml.project.util.DatabaseUtil;

@RestController
@RequestMapping(value = "api/act")
public class ActController {

	@Autowired
	UserService userService;

	private DatabaseClient databaseClient;
	private DatabaseUtil dUtil = new DatabaseUtil();


	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> saveAct(@RequestBody Dokument doc)
			throws JAXBException, IOException, SAXException {
		
		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(), dUtil.getDatabase(),
				dUtil.getUsername(), dUtil.getPassword(), dUtil.getAuthType());
		XMLDocumentManager xmlMenager = databaseClient.newXMLDocumentManager();
		
		String XML_FILE = "data/";
		
		/*
		 * User user = userService.findByUsername(principal.getName());
		 * String ime = user.getUsername();
		*/
		
		JAXBContext context = JAXBContext.newInstance("com.xml.project.jaxb");
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File(XML_FILE+"skupstina.xsd"));
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.setSchema(schema);

		Unmarshaller unmarshaller = context.createUnmarshaller();

		unmarshaller = context.createUnmarshaller();

		unmarshaller.setSchema(schema);
		
		
		File f = new File(XML_FILE+"/act.xml");
		
		FileOutputStream out = new FileOutputStream(f);
		System.out.println("3");
		
		m.marshal(doc, out);
		System.out.println("4");
		
		xmlMenager = databaseClient.newXMLDocumentManager();
		System.out.println("5");
		DocumentUriTemplate template = xmlMenager.newDocumentUriTemplate("xml");
		System.out.println("6");
		template.setDirectory("/acts/decisions/");
		System.out.println("7");
		
		InputStreamHandle handle = new InputStreamHandle(new FileInputStream(XML_FILE+"act.xml"));
		System.out.println("8");
		
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		System.out.println("9");
		metadata.getCollections().add("/parliament/acts/proposed");
		System.out.println("10");
		
		DocumentDescriptor desc = xmlMenager.create(template, metadata, handle);
		System.out.println("11");
		
		databaseClient.release();
		
		return new ResponseEntity<String>("Dokument je snimljen u bazu.", HttpStatus.OK);
	}
/*
	@RequestMapping(value = "/delete/{nazivDokumenta}/{tip}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteXML(Principal principal, @PathVariable String nazivDokumenta,
			@PathVariable String tip) throws FileNotFoundException, JAXBException {

		// konekcija sa bazom
		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(), dUtil.getDatabase(),
				dUtil.getUsername(), dUtil.getPassword(), dUtil.getAuthType());

		XMLDocumentManager xmlMenager = databaseClient.newXMLDocumentManager();
		User user = userService.findByUsername(principal.getName());
		System.out.println("korisnik " + user.getUsername());
		// System.out.println("korisnik je "+user);
		String art;
		if (tip.equals("act")) {
			art = "projekat/act/" + nazivDokumenta + ".xml";
			JAXBContext context = JAXBContext.newInstance("com.xml.project.jaxb");
			JAXBHandle<Dokument> hendle = new JAXBHandle<Dokument>(context);

			DocumentMetadataHandle metadeate = new DocumentMetadataHandle();

			xmlMenager.read(art, metadeate, hendle);

			Dokument dokument = hendle.get();
			System.out.println(dokument.getIme());
			if (dokument.getKorisnik().equalsIgnoreCase(user.getUsername())
					|| user.getRole().getRole().getName().equals("PRESIDENT")) {
				xmlMenager.delete(art);
				databaseClient.release();
				return new ResponseEntity<String>("File deleted", HttpStatus.OK);
			}
			return new ResponseEntity<String>("File isn't yours", HttpStatus.BAD_REQUEST);
		} else {
			art = "projekat/amandman/" + user.getUsername() + "/" + nazivDokumenta + ".xml";
			xmlMenager.delete(art);
			databaseClient.release();
			return new ResponseEntity<String>("File deleted", HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/amandman/", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> dodaj(@RequestBody Amandman amandman, Principal principal)
			throws IOException, JAXBException {

		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(), dUtil.getDatabase(),
				dUtil.getUsername(), dUtil.getPassword(), dUtil.getAuthType());
		XMLDocumentManager xmlMenager = databaseClient.newXMLDocumentManager();

		User user = userService.findByUsername(principal.getName());

		String ime = user.getUsername();
		Path path = Paths.get("./data/" + ime);
		if (Files.exists(path))
			return new ResponseEntity<String>("Amandman je vec dodat", HttpStatus.BAD_REQUEST);
		Files.createDirectories(path);

		String XML_FILE = "data/" + ime + "/" + amandman.getClan().getPodaciClana().getNaslov_clana() + ".xml";

		JAXBContext context = JAXBContext.newInstance(Amandman.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(amandman, new File(XML_FILE));

		String docId = "projekat/amandman/" + ime + "/" + amandman.getClan().getPodaciClana().getNaslov_clana()
				+ ".xml";
		docId = docId.replaceAll(" ", "_").toLowerCase();//ispravka
		InputStreamHandle handle = new InputStreamHandle(new FileInputStream(XML_FILE));
		xmlMenager.write(docId, handle);
		databaseClient.release();

		File file = new File(XML_FILE);
		file.delete();
		file = new File("./data/" + ime);
		file.delete();

		return new ResponseEntity<String>("dodat amandman", HttpStatus.OK);
	}
*/
}
