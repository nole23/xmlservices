package com.xml.project.jaxb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "clan")
public class Clan implements Serializable{

	private int id = 1;
	private PodaciClana podaciClana;
	private String opis;
	
	public Clan() {}
	
	
	public Clan(int id, PodaciClana podaciClana, String opis) {
		super();
		this.id = id;
		this.podaciClana = podaciClana;
		this.opis = opis;
	}


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
