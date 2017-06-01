package com.xml.project.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

	private DatabaseClient databaseClient;
	private DatabaseUtil dUtil = new DatabaseUtil();

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<String> vote(Principal principal, @RequestBody VoteDTO dto) {

		// konekcija sa bazom
		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(), dUtil.getDatabase(),
				dUtil.getUsername(), dUtil.getPassword(), dUtil.getAuthType());
		User user = userService.findByUsername(principal.getName());

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

	@RequestMapping(value = "/{docId}", method = RequestMethod.GET)
	public ResponseEntity<List<VoteDTO>> listVoit(Principal principal, @PathVariable String docId) {

		List<Voting> voting = votingService.findAll();
		
		List<VoteDTO> voteDTO = new ArrayList<>();
		for(Voting v: voting) {
			voteDTO.add(new VoteDTO(v));
		}

		return new ResponseEntity<List<VoteDTO>>(voteDTO, HttpStatus.OK);
	}
}
