package com.xml.project.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
import com.xml.project.dto.ClanoviDTO;
import com.xml.project.dto.JaxbDTO;
import com.xml.project.jaxb.Clan;
import com.xml.project.jaxb.Dokument;
import com.xml.project.jaxb.Glava;
import com.xml.project.jaxb.MestoDatum;
import com.xml.project.jaxb.PodaciClana;
import com.xml.project.jaxb.PodaciGlave;
import com.xml.project.jaxb.Propisi;
import com.xml.project.jaxb.Sadrzaj;
import com.xml.project.jaxb.SluzbeniList;
import com.xml.project.jaxb.UvodniDeo;
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
	 * Treba ispraviti kod, treba da prihvata xml i da moze prihvatiti vise xml
	 * istog imena
	 * 
	 * @param principal
	 * @param jaxbDTO
	 * @return
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/xml")
	public ResponseEntity<String> saveAct(Principal principal,
			@RequestBody JaxbDTO jaxbDTO) throws JAXBException,
			FileNotFoundException {

		/*
		 * //Otvaranje konekcije ka MarkLogic databaseClient =
		 * DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(),
		 * dUtil.getDatabase(), dUtil.getUsername(), dUtil.getPassword(),
		 * dUtil.getAuthType());
		 * 
		 * XMLDocumentManager xmlMenager =
		 * databaseClient.newXMLDocumentManager();
		 */
		// Ko dodaje dokument
		// User user = userService.findByUsername(principal.getName());

		String proba = jaxbDTO.getIme();
		// String proba1 = jaxbDTO.getSluzbeniList().getBroj_lista();

		System.out.println("ovo je ime koje smo uvezli " + proba);

		// sta je dodao
		/*MestoDatum mestoDatum = new MestoDatum();
		mestoDatum.setDatum(jaxbDTO.getDatum());
		mestoDatum.setMesto(jaxbDTO.getMesto());

		SluzbeniList sluzbeniList = new SluzbeniList();
		sluzbeniList.setBroj_lista(jaxbDTO.getBroj_lista());
		sluzbeniList.setMestoDatum(mestoDatum);

		PodaciGlave podaciGlave = new PodaciGlave();
		podaciGlave.setNaslov_glave(jaxbDTO.getNaslov_glave());
		podaciGlave.setBroj_glave(jaxbDTO.getBroj_glave());

		PodaciClana podaciClana = new PodaciClana();
		podaciClana.setNaslov_clana(jaxbDTO.getNaslov_clana());
		podaciClana.setBroj_clana(jaxbDTO.getBroj_clana());

		Clan clan = new Clan();
		clan.setOpis(jaxbDTO.getOpis());
		clan.setPodaciClana(podaciClana);

		Glava glava = new Glava();
		glava.setPodnaslovGlave(jaxbDTO.getPodnaslov_glave());
		glava.setPodaciGlave(podaciGlave);
		glava.setClan(clan);

		UvodniDeo uvodniDeo = new UvodniDeo();
		uvodniDeo.setGlava(glava);

		Propisi propisi = new Propisi();
		propisi.setUvodniDeo(uvodniDeo);

		Sadrzaj sadrzaj = new Sadrzaj();
		sadrzaj.setBroj_clana(jaxbDTO.getBroj_clana());
		sadrzaj.setNaziv_propisa(jaxbDTO.getNaziv_propisa());
		sadrzaj.setOdbornik(jaxbDTO.getOdbornik());

		Dokument dokument = new Dokument();
		dokument.setIme(jaxbDTO.getIme());
		dokument.setSluzbeniList(sluzbeniList);
		dokument.setPropisi(propisi);
		dokument.setSadzaj(sadrzaj);

		String XML_FILE = "data/dodato/" + jaxbDTO.getIme() + ".xml";

		// kreiranje xml fajla
		JAXBContext context = JAXBContext.newInstance(Dokument.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(dokument, new File(XML_FILE));

		// cuvanje u bazu taj xml
		/*
		 * String docId = "projekat/act/"+jaxbDTO.getIme()+".xml";
		 * InputStreamHandle handle = new InputStreamHandle(new
		 * FileInputStream("/data/dodato/"+jaxbDTO.getIme()+".xml"));
		 * xmlMenager.write(docId, handle); databaseClient.release();
		 */

		/*
		 * File file = new File("./data/dodato/"+jaxbDTO.getIme()+".xml");
		 * file.delete(); System.out.println("faj obrisan");
		 */

		// cuvanje u dom stablu

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/delete/{nazivDokumenta}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteXML(Principal principal,
			@PathVariable String nazivDokumenta) throws FileNotFoundException {

		// konekcija sa bazom
		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(),
				dUtil.getPort(), dUtil.getDatabase(), dUtil.getUsername(),
				dUtil.getPassword(), dUtil.getAuthType());
		XMLDocumentManager xmlMenager = databaseClient.newXMLDocumentManager();

		// Provera da li vlasnik dokumenta brise taj dokument
		// User user = userService.findByUsername(principal.getName());

		String art = "projekat/act/" + nazivDokumenta + ".xml";

		// brisanje
		xmlMenager.delete(art);

		databaseClient.release();

		return new ResponseEntity<String>("Obrisano", HttpStatus.OK);
	}

	@RequestMapping(value = "/testiram/sta/sam/uradio", method = RequestMethod.POST)
	public ResponseEntity<String> dodaj(@RequestBody Dokument doc)
			throws IOException, JAXBException {

		String ime = "stef";
		Path path = Paths.get("./data/" + ime); if (Files.exists(path));
		Files.createDirectories(path);
		
		String XML_FILE = "data/" + ime + "/"+doc.getIme()+".xml";
		
		JAXBContext context = JAXBContext.newInstance(Dokument.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(doc, new File(XML_FILE));
		
		/*List<Clan> clanovi  = new ArrayList<Clan>();
		for(int i=0; i<clan.getClans().size();i++){
			Clan cl = clan.getClans().get(i);
			clanovi.add(new Clan(Integer.parseInt(cl.getPodaciClana().getBroj_clana()),cl.getPodaciClana(),cl.getOpis()));
		}*/

		/*
		 * Path path = Paths.get("./data/" + ime); if (Files.exists(path))
		 * return new ResponseEntity<String>("lose", HttpStatus.BAD_REQUEST);
		 * Files.createDirectories(path);
		 * 
		 * MestoDatum mestoDatum = new MestoDatum();
		 * mestoDatum.setDatum(jax.getDatum());
		 * mestoDatum.setMesto(jax.getMesto());
		 * 
		 * SluzbeniList sluzbeniList = new SluzbeniList();
		 * sluzbeniList.setBroj_lista(jax.getBroj_lista());
		 * sluzbeniList.setMestoDatum(mestoDatum);
		 * sluzbeniList.setCena(jax.getCena());
		 * 
		 * Dokument dokument = new Dokument(); dokument.setIme(jax.getIme());
		 * dokument.setSluzbeniList(sluzbeniList);
		 * 
		 * String XML_FILE = "data/" + ime + "/"+jax.getIme()+".xml";
		 * 
		 * // kreiranje xml fajla JAXBContext context =
		 * JAXBContext.newInstance(Dokument.class); Marshaller m =
		 * context.createMarshaller();
		 * m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		 * m.marshal(dokument, new File(XML_FILE));
		 */

		return new ResponseEntity<String>(
				doc.getPropisi().getOdbornik(), HttpStatus.OK);
	}

}
