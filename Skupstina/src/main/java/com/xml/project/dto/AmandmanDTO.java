package com.xml.project.dto;

import com.xml.project.model.Amandman;
import com.xml.project.model.ProposalAct;
import com.xml.project.model.User;

public class AmandmanDTO {

	private Long id;
	private User user;
	private boolean isaccepted;
	private ProposalAct act;
	
	public AmandmanDTO() {}
	
	public AmandmanDTO(Long id, User user, boolean isaccepted, ProposalAct act) {
		super();
		this.id = id;
		this.user = user;
		this.isaccepted = isaccepted;
		this.act = act;
	}
	
	public AmandmanDTO(Amandman amandman) {
		this.id = amandman.getId();
		this.user = amandman.getUser();
		this.isaccepted = amandman.isIsaccepted();
		this.act = amandman.getAct();
	}

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

	public boolean isIsaccepted() {
		return isaccepted;
	}

	public void setIsaccepted(boolean isaccepted) {
		this.isaccepted = isaccepted;
	}

	public ProposalAct getAct() {
		return act;
	}

	public void setAct(ProposalAct act) {
		this.act = act;
	}

	@Override
	public String toString() {
		return "AmandmanDTO [id=" + id + ", user=" + user + ", isaccepted="
				+ isaccepted + ", act=" + act + "]";
	}
	
	
}
