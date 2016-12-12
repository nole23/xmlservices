package com.xml.project.model;

import java.sql.Blob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ProposalAct {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(cascade=CascadeType.REFRESH,fetch = FetchType.EAGER)
	private User user;
	
	@Column(nullable=false)
	private Date endDate;
	
	@Column(nullable=false)
	@Lob
	private Blob content;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "act", cascade = CascadeType.ALL)
	private Set<Amandman> amandmans = new HashSet<Amandman>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Blob getContent() {
		return content;
	}

	public void setContent(Blob content) {
		this.content = content;
	}

	public Set<Amandman> getAmandmans() {
		return amandmans;
	}

	public void setAmandmans(Set<Amandman> amandmans) {
		this.amandmans = amandmans;
	}
	
	
}
