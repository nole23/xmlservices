package com.xml.project.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.JAXBHandle;
import com.xml.project.jaxb.Amandman;
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

	/**
	 * @param principal
	 * @param jaxbDTO
	 * @return Dodavanje novog akta sa svim njegovim elementima i atributima
	 * @throws JAXBException
	 * @throws IOException
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> saveAct(Principal principal, @RequestBody Dokument doc)
			throws JAXBException, IOException {

		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(), dUtil.getDatabase(),
				dUtil.getUsername(), dUtil.getPassword(), dUtil.getAuthType());
		XMLDocumentManager xmlMenager = databaseClient.newXMLDocumentManager();

		User user = userService.findByUsername(principal.getName());
		if (!user.getUsername().equals(doc.getKorisnik()))
			return new ResponseEntity<String>("Nije vas akt", HttpStatus.BAD_REQUEST);

		String ime = user.getUsername();
		Path path = Paths.get("./data/" + ime);
		if (Files.exists(path))
			return new ResponseEntity<String>("Akt vec postoji", HttpStatus.BAD_REQUEST);
		Files.createDirectories(path);

		String XML_FILE = "data/" + ime + "/" + doc.getIme() + ".xml";

		JAXBContext context = JAXBContext.newInstance(Dokument.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(doc, new File(XML_FILE));

		String docId = "projekat/act/" + doc.getIme() + ".xml";

		InputStreamHandle handle = new InputStreamHandle(new FileInputStream(XML_FILE));
		xmlMenager.write(docId, handle);
		databaseClient.release();

		File file = new File("./data/" + ime + "/" + doc.getIme() + ".xml");
		file.delete();
		file = new File("./data/" + ime);
		file.delete();

		System.out.println("faj obrisan");

		return new ResponseEntity<String>("Dokument je snimljen u bazu.", HttpStatus.OK);
	}

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
			if (!dokument.getKorisnik().equalsIgnoreCase(user.getUsername()))
				return new ResponseEntity<String>("File isn't yours", HttpStatus.BAD_REQUEST);
		} else {
			art = "projekat/amandman/" + user.getUsername() + "/" + nazivDokumenta + ".xml";

		}

		xmlMenager.delete(art);
		databaseClient.release();
		return new ResponseEntity<String>("File deleted", HttpStatus.OK);
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

		InputStreamHandle handle = new InputStreamHandle(new FileInputStream(XML_FILE));
		xmlMenager.write(docId, handle);
		databaseClient.release();

		File file = new File(XML_FILE);
		file.delete();
		file = new File("./data/" + ime);
		file.delete();

		return new ResponseEntity<String>("dodat amandman", HttpStatus.OK);
	}

}
