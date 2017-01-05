package com.xml.project.dto;

import com.xml.project.model.Act;

public class ActDTO {

	private Long id;
	private String link;
	private UserDTO userDTO;
	
	public ActDTO() {}
	
	public ActDTO(Long id, String link, UserDTO userDTO) {
		super();
		this.id = id;
		this.link = link;
		this.userDTO = userDTO;
	}
	
	public ActDTO(Act act) {
		this.id = act.getId();
		this.link = act.getLink();
		this.userDTO = new UserDTO(act.getUser());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
}
