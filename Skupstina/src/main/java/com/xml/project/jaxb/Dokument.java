package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dokument")
public class Dokument {
	
	private String ime;
	private SluzbeniList sluzbeniList;
	private Propisi propisi;
	private Sadrzaj sadzaj;
	
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public SluzbeniList getSluzbeniList() {
		return sluzbeniList;
	}
	public void setSluzbeniList(SluzbeniList sluzbeniList) {
		this.sluzbeniList = sluzbeniList;
	}
	public Propisi getPropisi() {
		return propisi;
	}
	public void setPropisi(Propisi propisi) {
		this.propisi = propisi;
	}
	public Sadrzaj getSadzaj() {
		return sadzaj;
	}
	public void setSadzaj(Sadrzaj sadzaj) {
		this.sadzaj = sadzaj;
	}
}
