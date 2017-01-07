package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "clan")
public class Clan {

	private PodaciClana podaciClana;
	private String opis;
	
	public PodaciClana getPodaciClana() {
		return podaciClana;
	}
	public void setPodaciClana(PodaciClana podaciClana) {
		this.podaciClana = podaciClana;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
}
