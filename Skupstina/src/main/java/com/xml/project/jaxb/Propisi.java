package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "propisi")
public class Propisi {

	private String odbornik;
	private String preambula;
	private String naziv_propisa;
	
	private UvodniDeo uvodniDeo;
	private GlavniDeo glavniDeo;
	private ZavrsniDeo zavrsniDeo;
	
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
	public UvodniDeo getUvodniDeo() {
		return uvodniDeo;
	}
	public void setUvodniDeo(UvodniDeo uvodniDeo) {
		this.uvodniDeo = uvodniDeo;
	}
	public GlavniDeo getGlavniDeo() {
		return glavniDeo;
	}
	public void setGlavniDeo(GlavniDeo glavniDeo) {
		this.glavniDeo = glavniDeo;
	}
	public ZavrsniDeo getZavrsniDeo() {
		return zavrsniDeo;
	}
	public void setZavrsniDeo(ZavrsniDeo zavrsniDeo) {
		this.zavrsniDeo = zavrsniDeo;
	}
}
