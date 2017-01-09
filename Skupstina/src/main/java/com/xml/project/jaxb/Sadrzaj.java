package com.xml.project.jaxb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sadrzaj")
public class Sadrzaj implements Serializable{

	private String odbornik;
	private String broj_clana;
	private String naziv_propisa;
	
	public String getOdbornik() {
		return odbornik;
	}
	public void setOdbornik(String odbornik) {
		this.odbornik = odbornik;
	}
	public String getBroj_clana() {
		return broj_clana;
	}
	public void setBroj_clana(String broj_clana) {
		this.broj_clana = broj_clana;
	}
	public String getNaziv_propisa() {
		return naziv_propisa;
	}
	public void setNaziv_propisa(String naziv_propisa) {
		this.naziv_propisa = naziv_propisa;
	}
}
