package com.xml.project.dto;

import java.sql.Blob;

import com.xml.project.model.Act;
import com.xml.project.model.ActCategory;


public class ActDTO {

	private Long id;
	private String name;
	private Blob content;
	private ActCategory actCategory;
	
	public ActDTO() {}
	
	public ActDTO(Long id, String name, Blob content, ActCategory actCategory) {
		super();
		this.id = id;
		this.name = name;
		this.content = content;
		this.actCategory = actCategory;
	}
	
	public ActDTO(Act act) {
		this.id = act.getId();
		this.name = act.getName();
		this.content = act.getContent();
		this.actCategory = act.getActCategory();
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

	public Blob getContent() {
		return content;
	}

	public void setContent(Blob content) {
		this.content = content;
	}

	public ActCategory getActCategory() {
		return actCategory;
	}

	public void setActCategory(ActCategory actCategory) {
		this.actCategory = actCategory;
	}

	@Override
	public String toString() {
		return "ActDTO [id=" + id + ", name=" + name + ", content=" + content
				+ ", actCategory=" + actCategory + "]";
	}
}
