package com.xml.project.dto;

import com.xml.project.model.Voting;

public class VoteDTO {

	private Long id;
	private String tip;
	private String name;
	private boolean yn;

	public VoteDTO() {
	}

	public VoteDTO(Long id, String tip, String name, boolean yn) {
		super();
		this.id = id;
		this.tip = tip;
		this.name = name;
		this.yn = yn;
	}

	public VoteDTO(Voting v) {
		this.id = v.getId();
		this.tip = v.getTip();
		this.name = v.getName();
		this.yn = v.isYn();
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

}
