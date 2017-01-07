package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "podaci_clana")
public class PodaciClana {

	private String naslov_clana;
	private String broj_clana;
	
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
}
