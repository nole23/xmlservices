package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "broj_lista", "cena", "mesto_datum" })
@XmlRootElement(name = "naslov_sl_slista")
public class SluzbeniList {

	@XmlElement(name = "broj_lista", required = true)
	private String broj_lista;
	@XmlElement(name = "cena", required = true)
	private float cena;
	@XmlElement(name = "mesto_datum", required = true)
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
