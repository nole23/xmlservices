package com.xml.project.model;

import java.sql.Blob;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Act {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false)
	@Lob
	private Blob content;
	
	@ManyToOne(cascade=CascadeType.REFRESH,fetch = FetchType.EAGER)
	private ActCategory actCategory;

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
	
	
}
