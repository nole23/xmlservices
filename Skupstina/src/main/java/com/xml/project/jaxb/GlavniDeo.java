package com.xml.project.jaxb;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "glavni_deo")
public class GlavniDeo implements Serializable{

	private List<Glava> glava;

	public GlavniDeo() {

	}

	public List<Glava> getGlava() {
		return glava;
	}

	public void setGlava(List<Glava> glava) {
		this.glava = glava;
	}

}
