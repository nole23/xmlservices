package com.xml.project.dto;

import java.util.List;

import com.xml.project.jaxb.Clan;

public class ClanoviDTO {

	private List<Clan> clans;

	public ClanoviDTO() {
	}

	public List<Clan> getClans() {
		return clans;
	}

	public void setClans(List<Clan> clans) {
		this.clans = clans;
	}

}
