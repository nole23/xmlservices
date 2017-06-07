package com.xml.project.controller;

import java.security.Principal;

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
import com.xml.project.dto.MesagesDTO;
import com.xml.project.dto.UserDTO;
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

	/***
	 * 
	 * @param loginDTO
	 *            stores username and password of user
	 * @return returns the web token if data are correct, otherwise returns
	 *         "invalid login" message
	 * @see LoginDTO
	 * @author stefan
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<MesagesDTO> login(@RequestBody LoginDTO loginDTO) {
		MesagesDTO messagesDTO = new MesagesDTO();
		try {
			// Perform the authentication
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
					loginDTO.getPassword());
			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Reload user details so we can generate token
			UserDetails details = userDetailsService.loadUserByUsername(loginDTO.getUsername());
			
			messagesDTO.setJwt(tokenUtils.generateToken(details));
			return new ResponseEntity<MesagesDTO>(messagesDTO, HttpStatus.OK);
		} catch (Exception ex) {
			messagesDTO.setError("invalid");
			return new ResponseEntity<MesagesDTO>(messagesDTO, HttpStatus.NOT_FOUND);
		}
	}

	/***
	 * 
	 * @return populates the database with user roles and creates default user
	 *         with role of PRESIDENT
	 * @author stefan
	 */
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

	/***
	 * 
	 * @param userDTO
	 *            xml data format of user to be registered
	 * @return message if user is registered or error if user with given
	 *         username or email already exists
	 * @see UserDTO
	 * @author stefan
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/xml")
	public ResponseEntity<String> saveUser(@RequestBody UserDTO userDTO) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		User user = new User();
		User_Role user_role = new User_Role();

		if (userService.findByUsername(userDTO.getUsername()) != null)
			return new ResponseEntity<String>("User width {" + userDTO.getUsername() + "} username exists!",
					HttpStatus.CONFLICT);
		if (userService.findByEmail(userDTO.getEmail()) != null)
			return new ResponseEntity<String>("User width {" + userDTO.getEmail() + "} email exists!",
					HttpStatus.CONFLICT);
		// set user role
		user_role.setRole(roleRepository.findByName("ALDERMAN"));
		user.setRole(user_role);

		// set user credentials
		user.setEmail(userDTO.getEmail());
		user.setUsername(userDTO.getUsername());
		user.setPass(encoder.encode(userDTO.getPass()));
		user.setfName(userDTO.getfName());
		user.setlName(userDTO.getlName());

		user = userService.save(user);
		user_role.setUser(user);
		userRoleRepository.save(user_role);

		return new ResponseEntity<String>("User has been created", HttpStatus.CREATED);
	}
	
	/***
	 * 
	 * @param principal contains user data - this is from spring security
	 * @return xml data about registerd user
	 * @see Principal
	 * @author stefan
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.GET, produces = { "application/xml", "text/xml" })
	public ResponseEntity<UserDTO> getProfile(Principal principal) {

		User user = userService.findByUsername(principal.getName());
		return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.FOUND);
	}

}
