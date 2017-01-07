package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "uvodni_deo")
public class UvodniDeo {

	private Glava[] glava;

	public Glava[] getGlava() {
		return glava;
	}

	public void setGlava(Glava[] glava) {
		this.glava = glava;
	}
}
