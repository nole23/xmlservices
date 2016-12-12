package com.xml.project.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ActCategory {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "actCategory", cascade = CascadeType.ALL)
	private Set<Act> acts =  new HashSet<Act>();

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

	public Set<Act> getActs() {
		return acts;
	}

	public void setActs(Set<Act> acts) {
		this.acts = acts;
	}
	
	
}
