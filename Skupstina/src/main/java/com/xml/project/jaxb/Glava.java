package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "podaci_glave",
    "podnaslov_glave",
    "clan"
})
@XmlRootElement(name = "Glava")
public class Glava {

	@XmlElement(name = "id")
	private String id = "1";
	@XmlElement(name = "podaci_glave", required = true)
	private Glava.PodaciGlave podaciGlave;
	@XmlElement(name = "podnaslov_glave")
	private String podnaslovGlave;
	@XmlElement(name = "clan", required = true)
	private Clan[] clan;
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Glava.PodaciGlave getPodaciGlave() {
		return podaciGlave;
	}


	public void setPodaciGlave(Glava.PodaciGlave podaciGlave) {
		this.podaciGlave = podaciGlave;
	}


	public String getPodnaslovGlave() {
		return podnaslovGlave;
	}


	public void setPodnaslovGlave(String podnaslovGlave) {
		this.podnaslovGlave = podnaslovGlave;
	}


	public Clan[] getClan() {
		return clan;
	}


	public void setClan(Clan[] clan) {
		this.clan = clan;
	}


	@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "naslov_glave",
        "broj_glave"
    })
	@XmlRootElement(name = "podaci_glave")
	public static class PodaciGlave {
		
		@XmlElement(name = "naslov_glave", required = true)
        protected String naslovGlave;
        @XmlElement(name = "broj_glave", required = true)
        protected String broj_glave = "1";
        
		public String getNaslovGlave() {
			return naslovGlave;
		}
		public void setNaslovGlave(String naslovGlave) {
			this.naslovGlave = naslovGlave;
		}
		public String getBroj_glave() {
			return broj_glave;
		}
		public void setBroj_glave(String broj_glave) {
			this.broj_glave = broj_glave;
		}
        
        
	}
}
