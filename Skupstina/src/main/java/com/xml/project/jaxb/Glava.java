package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "glava")
public class Glava {

	private int id = 1;
	private PodaciGlave podaciGlave;
	private String podnaslovGlave;
	private Clan clan;
	
	@XmlAttribute(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public PodaciGlave getPodaciGlave() {
		return podaciGlave;
	}
	public void setPodaciGlave(PodaciGlave podaciGlave) {
		this.podaciGlave = podaciGlave;
	}
	public String getPodnaslovGlave() {
		return podnaslovGlave;
	}
	public void setPodnaslovGlave(String podnaslovGlave) {
		this.podnaslovGlave = podnaslovGlave;
	}
	public Clan getClan() {
		return clan;
	}
	public void setClan(Clan clan) {
		this.clan = clan;
	}


}
