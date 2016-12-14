package com.xml.project.dto;

import java.sql.Blob;
import java.util.Date;

import com.xml.project.model.ProposalAct;
import com.xml.project.model.User;

public class ProposalActDTO {

	private Long id;
	private User user;
	private Date endDate;
	private Blob content;
	
	public ProposalActDTO() {}
	
	public ProposalActDTO(Long id, User user, Date endDate, Blob content) {
		super();
		this.id = id;
		this.user = user;
		this.endDate = endDate;
		this.content = content;
	}
	
	public ProposalActDTO(ProposalAct proposalAct) {
		this.id = proposalAct.getId();
		this.user = proposalAct.getUser();
		this.endDate = proposalAct.getEndDate();
		this.content = proposalAct.getContent();
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

	@Override
	public String toString() {
		return "ProposalActDTO [id=" + id + ", user=" + user + ", endDate="
				+ endDate + ", content=" + content + "]";
	}
	
	
}
