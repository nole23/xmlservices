package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "clan")
public class Clan {

	private int id;
	private PodaciClana podaciClana;
	private String opis;
	
	@XmlAttribute(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
