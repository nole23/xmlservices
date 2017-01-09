package com.xml.project.jaxb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "podaci_glave")
public class PodaciGlave implements Serializable{

	private String naslov_glave;
	private String broj_glave;
	
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
}
