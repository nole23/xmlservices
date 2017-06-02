package com.xml.project.dto;

import com.xml.project.model.Role;
import com.xml.project.model.User_Role;

public class RoleDTO {

	private Long id;
	private String name;
	private User_Role role;
	
	public RoleDTO() {}
	
	public RoleDTO(Long id, String name, User_Role role) {
		super();
		this.id = id;
		this.name = name;
		this.role = role;
	}
	
	public RoleDTO(Role role) {
		this.id = role.getId();
		this.name = role.getName();
		this.role = role.getRole();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User_Role getRole() {
		return role;
	}

	public void setRole(User_Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "RoleDTO [id=" + id + ", name=" + name + ", role=" + role + "]";
	}
	
	
}
