package com.xml.project.dto;

import com.xml.project.model.Voting;

public class VoteDTO {

	private Long id;
	private String tip;
	private String name;
	private boolean yn;
	private UserDTO userDTO;
	private PublishedDTO publishedDTO;

	public VoteDTO() {
	}

	public VoteDTO(Long id, String tip, String name, boolean yn,
			UserDTO userDTO, PublishedDTO publishedDTO) {
		super();
		this.id = id;
		this.tip = tip;
		this.name = name;
		this.yn = yn;
		this.userDTO = userDTO;
		this.publishedDTO = publishedDTO;
	}

	public VoteDTO(Voting v) {
		this.id = v.getId();
		this.tip = v.getTip();
		this.name = v.getName();
		this.yn = v.isYn();
		if (v.getUser() != null)
			this.userDTO = new UserDTO(v.getUser());
		if (v.getPublished() != null)
			this.publishedDTO = new PublishedDTO(v.getPublished());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isYn() {
		return yn;
	}

	public void setYn(boolean yn) {
		this.yn = yn;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public PublishedDTO getPublishedDTO() {
		return publishedDTO;
	}

	public void setPublishedDTO(PublishedDTO publishedDTO) {
		this.publishedDTO = publishedDTO;
	}

}
