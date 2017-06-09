package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
		"naslovAkta",
		"sluzbeniListAmandmana",
		"dopunaZakonaAmandmana",
		"idAkta",
		"linkAkta",
		"Korisnik",
		"odobreno",
		"dopunaIzmena"
})
@XmlRootElement(name = "amandman")
public class Amandman {

	@XmlElement(name = "naslovAkta")
	private String naslovAkta;
	
	@XmlElement(name = "sluzbeniListAmandmana")
	private SluzbeniListAmandmana sluzbeniListAmandmana;
	
	@XmlElement(name = "dopunaZakonaAmandmana")
	private DopunaZakonaAmandamana dopunaZakonaAmandmana;
	
	@XmlAttribute(name = "idAkta", namespace = "http://www.parlament.gov.rs/amandmani")
	private String idAkta;
	
	@XmlAttribute(name = "linkAkta", namespace = "http://www.parlament.gov.rs/amandmani")
	private String linkAkta;
	
	@XmlAttribute(name = "Korisnik", namespace = "http://www.parlament.gov.rs/amandmani")
	private String Korisnik;
	
	@XmlAttribute(name = "odobreno", namespace = "http://www.parlament.gov.rs/amandmani")
	private boolean odobreno;
	
	@XmlAttribute(name = "dopunaIzmena", namespace = "http://www.parlament.gov.rs/amandmani")
	private boolean dopunaIzmena;

	public String getNaslovAkta() {
		return naslovAkta;
	}

	public void setNaslovAkta(String naslovAkta) {
		this.naslovAkta = naslovAkta;
	}

	public SluzbeniListAmandmana getSluzbeniListAmandmana() {
		return sluzbeniListAmandmana;
	}

	public void setSluzbeniListAmandmana(SluzbeniListAmandmana sluzbeniListAmandmana) {
		this.sluzbeniListAmandmana = sluzbeniListAmandmana;
	}

	public DopunaZakonaAmandamana getDopunaZakonaAmandmana() {
		return dopunaZakonaAmandmana;
	}

	public void setDopunaZakonaAmandmana(
			DopunaZakonaAmandamana dopunaZakonaAmandmana) {
		this.dopunaZakonaAmandmana = dopunaZakonaAmandmana;
	}

	public String getIdAkta() {
		return idAkta;
	}

	public void setIdAkta(String idAkta) {
		this.idAkta = idAkta;
	}

	public String getLinkAkta() {
		return linkAkta;
	}

	public void setLinkAkta(String linkAkta) {
		this.linkAkta = linkAkta;
	}

	public String getKorisnik() {
		return Korisnik;
	}

	public void setKorisnik(String korisnik) {
		Korisnik = korisnik;
	}

	public boolean isOdobreno() {
		return odobreno;
	}

	public void setOdobreno(boolean odobreno) {
		this.odobreno = odobreno;
	}

	public boolean isDopunaIzmena() {
		return dopunaIzmena;
	}

	public void setDopunaIzmena(boolean dopunaIzmena) {
		this.dopunaIzmena = dopunaIzmena;
	}
	
	
	
}
