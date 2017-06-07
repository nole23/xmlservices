package com.xml.project.dto;

public class MesagesDTO {

	private Long id;
	private String jwt;
	private String error;

	public MesagesDTO() {
		super();
	}
	
	public MesagesDTO(Long id, String jwt, String error) {
		super();
		this.id = id;
		this.jwt = jwt;
		this.error = error;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
