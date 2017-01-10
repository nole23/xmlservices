package com.xml.project.jaxb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "amandman")
public class Amandman implements Serializable {

	private String korisnik;//korisnik koji je predlozio izmenu
	private String dokumentLink;//link akta za koji vezujemo amandman
	private boolean odobren = false;//kada bude odobreno postaje true
	private String tip;//brisanje|dodavanje|izmena
	private Clan clan;//id clana se podudara sa id clana koji menjamo brisemo ili dodajemo

	@XmlAttribute
	public String getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(String korisnik) {
		this.korisnik = korisnik;
	}

	@XmlAttribute
	public String getDokumentLink() {
		return dokumentLink;
	}

	public void setDokumentLink(String dokumentLink) {
		this.dokumentLink = dokumentLink;
	}

	public boolean isOdobren() {
		return odobren;
	}

	public void setOdobren(boolean odobren) {
		this.odobren = odobren;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public Clan getClan() {
		return clan;
	}

	public void setClan(Clan clan) {
		this.clan = clan;
	}
}
