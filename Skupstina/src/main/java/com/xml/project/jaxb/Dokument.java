package com.xml.project.jaxb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dokument")
public class Dokument implements Serializable{
	
	private String ime;
	private String korisnik;
	private SluzbeniList sluzbeniList;
	private Propisi propisi;
	private Sadrzaj sadzaj;
	
	@XmlAttribute
	public String getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(String korisnik) {
		this.korisnik = korisnik;
	}
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
