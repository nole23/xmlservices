package com.xml.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xml.project.dto.LoginDTO;
import com.xml.project.model.Role;
import com.xml.project.model.User;
import com.xml.project.model.User_Role;
import com.xml.project.repository.RoleRepository;
import com.xml.project.repository.UserRoleRepository;
import com.xml.project.security.TokenUtils;
import com.xml.project.service.UserService;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	UserService userService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	/* USER LOGIN */
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
		try {
			// Perform the authentication
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
					loginDTO.getPassword());
			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Reload user details so we can generate token
			UserDetails details = userDetailsService.loadUserByUsername(loginDTO.getUsername());
			return new ResponseEntity<String>(tokenUtils.generateToken(details), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<String>("Invalid login", HttpStatus.NOT_FOUND);
		}
	}

	/* POPULATE THE DATABASE WITH ROLES AND ONE USER */
	@RequestMapping(value = "/populate", method = RequestMethod.GET)
	public ResponseEntity<String> populate() {

		String[] roles = { "PRESIDENT", "ALDERMAN" };
		Role role;
		// save roles
		for (int i = 0; i < roles.length; i++) {
			if (roleRepository.findByName(roles[i]) == null) {
				role = new Role();
				role.setName(roles[i]);
				roleRepository.save(role);
			}
		}
		// save user to database
		if (userService.findByUsername("president") == null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			User user = new User();
			user.setEmail("pres@gmail.com");
			user.setfName("Stefan");
			user.setlName("Plazic");
			user.setUsername("president");
			user.setPass(encoder.encode("stefan"));

			userService.save(user);

			User_Role user_Role = new User_Role();
			user_Role.setRole(roleRepository.findByName(roles[0]));
			user_Role.setUser(user);
			userRoleRepository.save(user_Role);
		}

		return new ResponseEntity<String>("Succesfully created user roles", HttpStatus.CREATED);
	}
}
