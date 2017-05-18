package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { 
		"brojc_clana", 
		"naziv_propisa"
})
@XmlRootElement(name = "sadrzaj")
public class Sadrzaj {

	@XmlElement(name = "broj_clana", required = true)
	private String broj_clana;
	@XmlElement(name = "naziv_propisa", required = true)
	private String naziv_propisa;
	@XmlAttribute(name = "id", namespace = "http://www.parlament.gov.rs/amandmani", required = true)
	private String id;
	@XmlAttribute(name = "Korisnik", required = true)
	private String korisnik;
	
}
