package com.xml.project.dto;

import java.util.Date;

public class JaxbDTO {

	private String ime;
	private String broj_lista;
	private float cena;
	private String mesto;
	private String datum;
	private String odbornik;
	private String preambula;
	private String naziv_propisa;
	private String naslov_glave;
	private String broj_glave;
	private String podnaslov_glave;
	private String naslov_clana;
	private String broj_clana;
	private String opis;
	
	public JaxbDTO(String ime, String broj_lista, float cena, String mesto, String datum, String odbornik, String preambula,
			String naziv_propisa, String naslov_glave, String broj_glave, String podnaslov_glave, String naslov_clana, String broj_clana, String opis) {
		super();
		this.ime = ime;
		this.broj_lista = broj_lista;
		this.cena = cena;
		this.mesto = mesto;
		this.datum = datum;
		this.odbornik = odbornik;
		this.preambula = preambula;
		this.naziv_propisa = naziv_propisa;
		this.naslov_glave = naslov_glave;
		this.broj_glave = broj_glave;
		this.podnaslov_glave = podnaslov_glave;
		this.naslov_clana = naslov_clana;
		this.broj_clana = broj_clana;
		this.opis = opis;
	}
	
	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public JaxbDTO() {}

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

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getOdbornik() {
		return odbornik;
	}

	public void setOdbornik(String odbornik) {
		this.odbornik = odbornik;
	}

	public String getPreambula() {
		return preambula;
	}

	public void setPreambula(String preambula) {
		this.preambula = preambula;
	}

	public String getNaziv_propisa() {
		return naziv_propisa;
	}

	public void setNaziv_propisa(String naziv_propisa) {
		this.naziv_propisa = naziv_propisa;
	}

	public String getNaslov_glave() {
		return naslov_glave;
	}

	public void setNaslov_glave(String naslov_glave) {
		this.naslov_glave = naslov_glave;
	}

	public String getBroj_glave() {
		return broj_glave;
	}

	public void setBroj_glave(String broj_glave) {
		this.broj_glave = broj_glave;
	}

	public String getPodnaslov_glave() {
		return podnaslov_glave;
	}

	public void setPodnaslov_glave(String podnaslov_glave) {
		this.podnaslov_glave = podnaslov_glave;
	}

	public String getNaslov_clana() {
		return naslov_clana;
	}

	public void setNaslov_clana(String naslov_clana) {
		this.naslov_clana = naslov_clana;
	}

	public String getBroj_clana() {
		return broj_clana;
	}

	public void setBroj_clana(String broj_clana) {
		this.broj_clana = broj_clana;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}
}
