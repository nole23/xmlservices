package com.xml.project.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.InputStreamHandle;
import com.xml.project.util.DatabaseUtil;

@RestController
@RequestMapping(value = "test")
public class TestController {

	private DatabaseClient databaseClient;
	private DatabaseUtil dUtil = new DatabaseUtil();

	/**
	 * Cuvanje seme u MarkLogic bazu
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<String> saveAdmin() throws FileNotFoundException {
		
		
		 databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(),
		 dUtil.getPort(), dUtil.getDatabase(), dUtil.getUsername(), dUtil.getPassword(),
		 dUtil.getAuthType());
		 
		 XMLDocumentManager xmlMenager = databaseClient.newXMLDocumentManager();
		 
		 //Pod kojim imenom ce sacuvati u Bazu podatak
		 String docId = "/example/books.xml";
		 
		 //XML koji zelimo da sacuvamo u bazu
		 InputStreamHandle handle = new InputStreamHandle(new FileInputStream("data/books.xml"));
		 
		 //Pisanje u bazu
		 xmlMenager.write(docId, handle);
		 
		 databaseClient.release();

		return new ResponseEntity<String>("Dodato", HttpStatus.OK);
	}
	
	/**
	 * Brisanje iz MarkLogic baze
	 * @return
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.PUT)
	public ResponseEntity<String> deleteXML() throws FileNotFoundException {
		 databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(),
		 dUtil.getPort(), dUtil.getDatabase(), dUtil.getUsername(), dUtil.getPassword(),
		 dUtil.getAuthType());
		 
		 XMLDocumentManager xmlMenager = databaseClient.newXMLDocumentManager();
		 
		 //Dokument u bazi koji zelimo da izbrisemo
		 String docId = "/example/books.xml";
		 
		 //brisanje iz baze dati dokument
		 xmlMenager.delete(docId);
		 
		 databaseClient.release();

		return new ResponseEntity<String>("Obrisano", HttpStatus.OK);
	}
	
}
