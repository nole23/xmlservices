package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "glava")
public class Glava {

	private PodaciGlave podaciGlave;
	private String podnaslovGlave;
	private Clan clan;
	
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
