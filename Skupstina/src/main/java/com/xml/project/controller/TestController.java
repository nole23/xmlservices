package com.xml.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marklogic.client.DatabaseClient;
import com.xml.project.util.DatabaseUtil;

@RestController
@RequestMapping(value = "test")
public class TestController {

	private DatabaseClient databaseClient;
	private DatabaseUtil dUtil = new DatabaseUtil();

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ResponseEntity<String> saveAdmin() {
		// databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(),
		// dUtil.getPort(), dUtil.getUsername(), dUtil.getPassword(),
		// dUtil.getAuthType());

		return new ResponseEntity<String>(dUtil.getAuth_type() != null ? dUtil.getAuth_type() : "Null", HttpStatus.OK);
	}
}
