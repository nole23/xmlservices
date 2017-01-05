package com.xml.project.dto;

import com.xml.project.model.User;

public class UserDTO {

	private Long id;
	private String lName;
	private String fName;
	private String username;
	private String email;
	private String pass;
	private RoleDTO role;

	public UserDTO() {
	}

	public UserDTO(Long id, String lName, String fName, String username, String email, String pass, RoleDTO role) {
		super();
		this.id = id;
		this.lName = lName;
		this.fName = fName;
		this.username = username;
		this.email = email;
		this.pass = pass;
		this.role = role;
	}

	public UserDTO(User user) {
		this.id = user.getId();
		this.lName = user.getlName();
		this.fName = user.getfName();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.pass = user.getPass();
		this.role = new RoleDTO();
		this.role.setName(user.getRole().getRole().getName());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public RoleDTO getRole() {
		return role;
	}

	public void setRole(RoleDTO role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", lName=" + lName + ", fName=" + fName + ", username=" + username + ", email="
				+ email + ", pass=" + pass + ", role=" + role + "]";
	}
}
