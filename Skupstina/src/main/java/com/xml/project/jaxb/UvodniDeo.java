package com.xml.project.jaxb;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "uvodni_deo")
public class UvodniDeo implements Serializable{

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
