package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {}
	
	public Dokument createDokument() {
		return new Dokument();
	}
	
}
