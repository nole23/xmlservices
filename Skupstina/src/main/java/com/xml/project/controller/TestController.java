package com.xml.project.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.jni.FileInfo;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.marker.XMLWriteHandle;
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
	 * @throws IOException 
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<String> saveAdmin(@RequestParam("uploadfile") MultipartFile uploadfile) throws IOException {
		String filename = uploadfile.getOriginalFilename();
	   
  
	    String directory = "./data/dodato";
	    String filepath = Paths.get(directory, filename).toString();
	    
	    // Save the file locally
	    BufferedOutputStream stream =
	        new BufferedOutputStream(new FileOutputStream(new File(filepath)));
	    stream.write(uploadfile.getBytes());
	    stream.close();
		  
		
		
		 databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(),
		 dUtil.getPort(), dUtil.getDatabase(), dUtil.getUsername(), dUtil.getPassword(),
		 dUtil.getAuthType());
		 
		 XMLDocumentManager xmlMenager = databaseClient.newXMLDocumentManager();
		 
		 //Pod kojim imenom ce sacuvati u Bazu podatak
		 String docId = "/example/books.xml";
		 
		 //XML koji zelimo da sacuvamo u bazu
		 InputStreamHandle handle = new InputStreamHandle(new FileInputStream("/data/dodato/"+filename));
		 
		 //Pisanje u bazu
		 xmlMenager.write(docId, handle);
		 
		 databaseClient.release();
		
		return new ResponseEntity<String>("Dobro "+filename,HttpStatus.OK);
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
