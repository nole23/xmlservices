package com.xml.project.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.InputStreamHandle;
import com.xml.project.model.Act;
import com.xml.project.model.User;
import com.xml.project.repository.ActRepository;
import com.xml.project.service.UserService;
import com.xml.project.util.DatabaseUtil;

@RestController
@RequestMapping(value = "api/act")
public class ActController {

	private DatabaseClient databaseClient;
	private DatabaseUtil dUtil = new DatabaseUtil();
	
	@Autowired
	UserService userService;
	
	@Autowired
	ActRepository actRepository;
	
	/**
	 * 
	 * @param principal korisnik
	 * @param uploadfile fajl koji ucitavamo
	 * @param tip da li je amandman ili akt
	 * @return cuvamo prvo na serveru fajl kreiramo ime za cuvanje u MarkLogic bazi sacuvamo u bazu i na kraju obrisemo iz
	 * 		   servera
	 * @throws IOException
	 * 
	 * Treba samo zastiti od nezeljeni ispada
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/xml", "text/xml" })
	public ResponseEntity<String> saveAct(Principal principal, @RequestParam("uploadfile") MultipartFile uploadfile) throws IOException {
		
		//konekcija sa MarckLogic bazom
		 databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(),
				 dUtil.getPort(), dUtil.getDatabase(), dUtil.getUsername(), dUtil.getPassword(),
				 dUtil.getAuthType());
		 
		 XMLDocumentManager xmlMenager = databaseClient.newXMLDocumentManager();
		
		//ko je dodao akt
		User user = userService.findByUsername(principal.getName());
		
		//Metoda koja cuva na serveru fajl kreira ime za cuvanje tog fajla
		String filename = uploadfile.getOriginalFilename();
		String directory = "./data/dodato";
	    String filepath = Paths.get(directory, filename).toString();
		System.out.println("dodato na server sa putanjom: "+filepath);
		
	    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
	    stream.write(uploadfile.getBytes());
	    stream.close();
	    
	    String docId = "projekat/act/"+filename;
		System.out.println("docId je "+docId);
		    
		//Ucitati i sacuvati taj xml u MarckLogic bazu
	    InputStreamHandle handle = new InputStreamHandle(new FileInputStream("/data/dodato/"+filename));
	    xmlMenager.write(docId, handle);
	    databaseClient.release();
	    
		//sacuvati u mySql ko je dodao i sta je dodao(link)
	    Act act = new Act();
	    act.setLink(docId);
	    act.setUser(user);
	    actRepository.save(act);
	    
	    //brisemo sa servera fajl koji smo dodali
	    File file = new File("./data/dodato/"+filename);
	    file.delete();
	    System.out.println("faj obrisan");
		
		return new ResponseEntity<String>("docId je "+filename,HttpStatus.OK);
	}
	
	/**
	 * Brisanje akta iz MarkLogic baze podataka
	 * @param principal korisnik koji brise datoteku
	 * @param nazivDokumenta link datoteke koja se brise
	 * @return obrisana datoteka
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "/delete/{nazivDokumenta}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteXML(Principal principal, @PathVariable String nazivDokumenta) throws FileNotFoundException {
		
		//konekcija sa bazom
		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(),
				dUtil.getPort(), dUtil.getDatabase(), dUtil.getUsername(), dUtil.getPassword(),
				dUtil.getAuthType());
		XMLDocumentManager xmlMenager = databaseClient.newXMLDocumentManager();
		
		//Provera da li vlasnik dokumenta brise taj dokument
		User user = userService.findByUsername(principal.getName());
		
		//pronalazenje u mySql bazi putanju do dokumenta koji brisemo
		Act art = actRepository.findByLink(nazivDokumenta);
		Long idUser = art.getUser().getId();
		String docId = art.getLink();
		if(user.getId() == idUser){
			//brisanje datog dokumenta iz MarkLogic baze
			xmlMenager.delete(docId);
		}

		//brisanje iz MySql baze
		actRepository.remove(art);
		 
		 databaseClient.release();

		return new ResponseEntity<String>("Obrisano", HttpStatus.OK);
	}
}
