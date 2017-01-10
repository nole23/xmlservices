package com.xml.project.controller;

import java.io.FileNotFoundException;
import java.security.Principal;
import java.util.List;

import javax.xml.bind.JAXBException;

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
import com.marklogic.client.document.DocumentPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.marker.DocumentPatchHandle;
import com.marklogic.client.util.EditableNamespaceContext;
import com.xml.project.dto.VoteDTO;
import com.xml.project.model.User;
import com.xml.project.model.Voting;
import com.xml.project.service.UserService;
import com.xml.project.service.VotingService;
import com.xml.project.util.DatabaseUtil;

@RestController
@RequestMapping(value = "api/voting")
public class VoteController {

	@Autowired
	UserService userService;

	@Autowired
	VotingService votingService;

	@Autowired
	ActController actControler;

	private DatabaseClient databaseClient;
	private DatabaseUtil dUtil = new DatabaseUtil();

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<String> vote(Principal principal, @RequestBody VoteDTO dto) {

		// konekcija sa bazom
		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(), dUtil.getDatabase(),
				dUtil.getUsername(), dUtil.getPassword(), dUtil.getAuthType());
		User user = userService.findByUsername(principal.getName());
		// proveri da li su tipovi uredu
		if (!dto.getTip().equalsIgnoreCase("amandman") && !dto.getTip().equalsIgnoreCase("act"))
			return new ResponseEntity<String>("Tip moze biti amandman ili act", HttpStatus.BAD_REQUEST);
		// proveri da li je korisnik vec glasao za dato ime
		List<Voting> votes = votingService.findByName(dto.getName());
		for (Voting vot : votes) {
			if (vot.getUser() == user)
				return new ResponseEntity<String>("Vec ste glasali za dati dokument", HttpStatus.CONFLICT);
		}

		Voting voting = new Voting();
		voting.setName(dto.getName());
		voting.setTip(dto.getTip());
		voting.setUser(user);
		voting.setYn(dto.isYn());

		votingService.save(voting);

		return new ResponseEntity<String>("Glasali ste", HttpStatus.OK);
	}

	@RequestMapping(value = "/{odluka}/{imeAkt}", method = RequestMethod.GET)
	public ResponseEntity<String> usvajanjeAkta(Principal principal, @PathVariable String odluka,
			@PathVariable String imeAkt) throws JAXBException, FileNotFoundException {

		// konekcija sa bazom
		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(), dUtil.getDatabase(),
				dUtil.getUsername(), dUtil.getPassword(), dUtil.getAuthType());
		XMLDocumentManager xmlMenager = databaseClient.newXMLDocumentManager();

		User user = userService.findByUsername(principal.getName());
		if (!user.getRole().getRole().getName().equals("PRESIDENT"))
			return new ResponseEntity<String>("Niste presednik", HttpStatus.BAD_REQUEST);

		if (odluka.equals("prihvatio")) {

			String docId = "projekat/act/" + imeAkt + ".xml";

			DocumentPatchBuilder patchBuilder = xmlMenager.newPatchBuilder();

			EditableNamespaceContext namespaces = new EditableNamespaceContext();

			patchBuilder = xmlMenager.newPatchBuilder();
			patchBuilder.setNamespaces(namespaces);

			DocumentPatchHandle patchHandle = patchBuilder.build();

			String contextXPath1 = "//dokument/odobreno";

			String patch2 = "<odobreno>true</odobreno>";

			patchBuilder.replaceFragment(contextXPath1, patch2);

			patchHandle = patchBuilder.build();

			xmlMenager.patch(docId, patchHandle);

			// Release the client
			databaseClient.release();

			System.out.println("[INFO] End.");

			return new ResponseEntity<String>("Odobren akt", HttpStatus.OK);

		} else if (odluka.equals("odbio")) {

			return this.actControler.deleteXML(principal, imeAkt, "act");
		} else {
			return new ResponseEntity<String>("Los zahtev", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/amandman/{odluka}/{imeAmand}", method = RequestMethod.GET)
	public ResponseEntity<String> usvajanjeAmandmana(Principal principal, @PathVariable String odluka,
			@PathVariable String imeAmand) {

		// konekcija sa bazom
		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(), dUtil.getDatabase(),
				dUtil.getUsername(), dUtil.getPassword(), dUtil.getAuthType());
		XMLDocumentManager xmlMenager = databaseClient.newXMLDocumentManager();
		User user = userService.findByUsername(principal.getName());
		if (!user.getRole().getRole().getName().equals("PRESIDENT"))
			return new ResponseEntity<String>("Niste predsednik", HttpStatus.BAD_REQUEST);

		
		return new ResponseEntity<String>("Odobren akt", HttpStatus.OK);
	}

}
