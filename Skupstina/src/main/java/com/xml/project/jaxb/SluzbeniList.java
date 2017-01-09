package com.xml.project.jaxb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sluzbeni_list")
public class SluzbeniList implements Serializable{

	private String broj_lista;
	private float cena;
	private MestoDatum mestoDatum;
	
	public String getBroj_lista() {
		return broj_lista;
	}
	public void setBroj_lista(String broj_lista) {
		this.broj_lista = broj_lista;
	}
	public float getCena() {
		return cena;
	}
	public void setCena(float cena) {
		this.cena = cena;
	}
	public MestoDatum getMestoDatum() {
		return mestoDatum;
	}
	public void setMestoDatum(MestoDatum mestoDatum) {
		this.mestoDatum = mestoDatum;
	}
}
