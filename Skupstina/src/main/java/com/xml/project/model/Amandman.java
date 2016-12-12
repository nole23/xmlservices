package com.xml.project.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Amandman {
	
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(cascade=CascadeType.REFRESH,fetch = FetchType.EAGER)
	private User user;
	
	@Column(nullable=false)
	private boolean isaccepted;
	
	@ManyToOne(cascade=CascadeType.REFRESH,fetch = FetchType.EAGER)
	private ProposalAct act;

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

	public ProposalAct getAct() {
		return act;
	}

	public void setAct(ProposalAct act) {
		this.act = act;
	}

	public boolean isIsaccepted() {
		return isaccepted;
	}

	public void setIsaccepted(boolean isaccepted) {
		this.isaccepted = isaccepted;
	}
	
	
	
}
