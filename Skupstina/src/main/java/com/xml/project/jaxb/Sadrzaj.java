package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { 
		"broj_clana", 
		"naziv_propisa",
		"id",
		"korisnik"
})
@XmlRootElement(name = "sadrzaj")
public class Sadrzaj {

	@XmlElement(name = "broj_clana")
	private String broj_clana;
	@XmlElement(name = "naziv_propisa")
	private String naziv_propisa;
	@XmlAttribute(name = "id", namespace = "http://www.parlament.gov.rs/amandmani")
	private String id;
	@XmlAttribute(name = "Korisnik")
	private String korisnik;
	
}
