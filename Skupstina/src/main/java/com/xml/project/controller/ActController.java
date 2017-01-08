package com.xml.project.controller;

import java.io.File;
import java.io.FileNotFoundException;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
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
import com.xml.project.model.User;
import com.xml.project.service.UserService;
import com.xml.project.util.DatabaseUtil;

@RestController
@RequestMapping(value = "api/act")
public class ActController {

	@Autowired
	UserService userService;
	
	private Document document;
	
	private static String TARGET_NAMESPACE = "http://www.ftn.uns.ac.rs/zavrsni_rad";

	private static String XSI_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
	
	private DatabaseClient databaseClient;
	private DatabaseUtil dUtil = new DatabaseUtil();
	
	/**
	 * Treba ispraviti kod, treba da prihvata xml i da moze prihvatiti vise xml istog imena
	 * @param principal
	 * @param jaxbDTO
	 * @return
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/xml")
	public ResponseEntity<String> saveAct(Principal principal, @RequestBody JaxbDTO jaxbDTO) throws JAXBException, FileNotFoundException {
		
		//Otvaranje konekcije ka MarkLogic
		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(),
				 dUtil.getPort(), dUtil.getDatabase(), dUtil.getUsername(), dUtil.getPassword(),
				 dUtil.getAuthType());
		 
		 XMLDocumentManager xmlMenager = databaseClient.newXMLDocumentManager();
		
		//Ko dodaje dokument
		//User user = userService.findByUsername(principal.getName());
		
		String proba = jaxbDTO.getIme();
		//String proba1 = jaxbDTO.getSluzbeniList().getBroj_lista();
		
		System.out.println("ovo je ime koje smo uvezli "+proba);
		 
		//sta je dodao 
		MestoDatum mestoDatum = new MestoDatum();
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
		
		String XML_FILE = "data/dodato/"+jaxbDTO.getIme()+".xml";

		//kreiranje xml fajla
		JAXBContext context = JAXBContext.newInstance(Dokument.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(dokument, new File(XML_FILE));
		
		//cuvanje u bazu taj xml
		/*String docId = "projekat/act/"+jaxbDTO.getIme()+".xml";
		InputStreamHandle handle = new InputStreamHandle(new FileInputStream("/data/dodato/"+jaxbDTO.getIme()+".xml"));
	    xmlMenager.write(docId, handle);
	    databaseClient.release();*/
		
	    /*File file = new File("./data/dodato/"+jaxbDTO.getIme()+".xml");
	    file.delete();
	    System.out.println("faj obrisan");*/
	    
		//cuvanje u dom stablu
	
		return new ResponseEntity<String>(HttpStatus.OK);
	}

		
	@RequestMapping(value = "/delete/{nazivDokumenta}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteXML(Principal principal, @PathVariable String nazivDokumenta) throws FileNotFoundException {
		
		//konekcija sa bazom
		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(),
				dUtil.getPort(), dUtil.getDatabase(), dUtil.getUsername(), dUtil.getPassword(),
				dUtil.getAuthType());
		XMLDocumentManager xmlMenager = databaseClient.newXMLDocumentManager();
		
		//Provera da li vlasnik dokumenta brise taj dokument
		User user = userService.findByUsername(principal.getName());
		
		String art = "projekat/act/"+nazivDokumenta+".xml";

		//brisanje 
		xmlMenager.delete(art);
		 
		databaseClient.release();

		return new ResponseEntity<String>("Obrisano", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/testiram/sta/sam/uradio", method = RequestMethod.GET)
	public ResponseEntity<String> dodaj() throws FileNotFoundException, JAXBException {
		Element rad = document.createElementNS(TARGET_NAMESPACE, "rad");
		document.appendChild(rad);
		
		rad.setAttributeNS(XSI_NAMESPACE, "xsi:schemaLocation", "http://www.ftn.uns.ac.rs/zavrsni_rad ../xsd/zavrsni_rad.xsd");
		rad.setAttribute("vrsta_rada", "Diplomski rad");		
		
		Element naslovnaStrana = document.createElementNS(TARGET_NAMESPACE, "naslovna_strana");
		rad.appendChild(naslovnaStrana);
		
		Element institucija = document.createElementNS(TARGET_NAMESPACE, "institucija");
		naslovnaStrana.appendChild(institucija);
		
		Element univerzitet = document.createElementNS(TARGET_NAMESPACE, "univerzitet");
		univerzitet.appendChild(document.createTextNode("Univerzitet u Novom Sadu"));
		institucija.appendChild(univerzitet);

		Element fakultet = document.createElementNS(TARGET_NAMESPACE, "fakultet");
		fakultet.appendChild(document.createTextNode("Fakultet tehničkih nauka"));
		institucija.appendChild(fakultet);
		
		Element departman = document.createElementNS(TARGET_NAMESPACE, "departman");
		departman.appendChild(document.createTextNode("Računarstvo i automatika"));
		institucija.appendChild(departman);

		Element katedra = document.createElementNS(TARGET_NAMESPACE, "katedra");
		katedra.appendChild(document.createTextNode("Katedra za informatiku"));
		institucija.appendChild(katedra);
		
		Element autor = document.createElementNS(TARGET_NAMESPACE, "autor");
		naslovnaStrana.appendChild(autor);
		
		Element ime = document.createElementNS(TARGET_NAMESPACE, "ime");
		ime.appendChild(document.createTextNode("Petar"));
		autor.appendChild(ime);
		
		Element prezime = document.createElementNS(TARGET_NAMESPACE, "prezime");
		prezime.appendChild(document.createTextNode("Petrović"));
		autor.appendChild(prezime);
		
		Element broj_indeksa = document.createElementNS(TARGET_NAMESPACE, "broj_indeksa");
		broj_indeksa.appendChild(document.createTextNode("RA 1/2012"));
		autor.appendChild(broj_indeksa);
		
		Element temaSrpski = document.createElementNS(TARGET_NAMESPACE, "tema_rada");
		temaSrpski.setAttribute("jezik", "srpski");
		temaSrpski.appendChild(document.createTextNode("Implementacija podsistema banke u okviru sistema platnog prometa."));
		naslovnaStrana.appendChild(temaSrpski);

		Element temaEngleski = document.createElementNS(TARGET_NAMESPACE, "tema_rada");
		temaEngleski.setAttribute("jezik", "engleski");
		temaEngleski.appendChild(document.createTextNode("Implementation of banking subsystem in an electronic payment system."));
		naslovnaStrana.appendChild(temaEngleski);
		
		Element nivoStudija = document.createElementNS(TARGET_NAMESPACE, "nivo_studija");
		nivoStudija.appendChild(document.createTextNode("OAS"));
		naslovnaStrana.appendChild(nivoStudija);
		
		Element sadrzaj = document.createElementNS(TARGET_NAMESPACE, "sadrzaj");
		sadrzaj.appendChild(document.createComment("Generisati \"sadrzaj\" analogno."));
		rad.appendChild(sadrzaj);
		
		Element poglavlja = document.createElementNS(TARGET_NAMESPACE, "poglavlja");
		poglavlja.appendChild(document.createComment("Generisati \"poglavlja\" analogno."));
		rad.appendChild(poglavlja);
		
		return new ResponseEntity<String>("dodato", HttpStatus.OK);
	}
	
}
