package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "glavni_deo")
public class GlavniDeo {

	private Glava glava;

	public Glava getGlava() {
		return glava;
	}

	public void setGlava(Glava glava) {
		this.glava = glava;
	}
}
