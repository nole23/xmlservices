package com.xml.project.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "uvodni_deo")
public class UvodniDeo {

	private List<Glava> glava;

	public UvodniDeo() {
	}

	public List<Glava> getGlava() {
		return glava;
	}

	public void setGlava(List<Glava> glava) {
		this.glava = glava;
	}

}
