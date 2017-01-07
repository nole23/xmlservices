package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "zavrsni_deo")
public class ZavrsniDeo {

	private Glava glava;
	private String potpis_presednika;
	
	public Glava getGlava() {
		return glava;
	}
	public void setGlava(Glava glava) {
		this.glava = glava;
	}
	public String getPotpis_presednika() {
		return potpis_presednika;
	}
	public void setPotpis_presednika(String potpis_presednika) {
		this.potpis_presednika = potpis_presednika;
	}
}
