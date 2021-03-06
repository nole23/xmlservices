//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.22 at 01:59:20 PM CET 
//


package com.xml.project.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the rs.gov.parlament.propisi.entities package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Ref_QNAME = new QName("http://www.parlament.gov.rs/amandmani", "Ref");
    private final static QName _Label_QNAME = new QName("http://www.parlament.gov.rs/amandmani", "Label");
    private final static QName _Title_QNAME = new QName("http://www.parlament.gov.rs/amandmani", "Title");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: rs.gov.parlament.propisi.entities
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Article }
     * 
     */
    public SluzbeniList createSluzbeniList() {
        return new SluzbeniList();
    }

    /**
     * Create an instance of {@link Part }
     * 
     */
    public Propisi createPropisi() {
        return new Propisi();
    }

    /**
     * Create an instance of {@link Section }
     * 
     */
    public Sadrzaj createSadrzaj() {
        return new Sadrzaj();
    }

    /**
     * Create an instance of {@link Article.Content }
     * 
     */


    /**
     * Create an instance of {@link Subpoint }
     * 
     */
    public UvodniDeo createUvodniDeo() {
        return new UvodniDeo();
    }

    /**
     * Create an instance of {@link Indent }
     * 
     */
    public GlavniDeo createGlavniDeo() {
        return new GlavniDeo();
    }

    /**
     * Create an instance of {@link Act }
     * 
     */
    public ZavrsniDeo createZavrsniDeo() {
        return new ZavrsniDeo();
    }

    /**
     * Create an instance of {@link Part.Chapter }
     * 
     */
    public Dokument createDokument() {
        return new Dokument();
    }

    /**
     * Create an instance of {@link Point }
     * 
     */
    public Glava createGlava() {
        return new Glava();
    }

    /**
     * Create an instance of {@link Paragraph }
     * 
     */
    public PodaciGlave createPodaciGlave() {
        return new PodaciGlave();
    }
    
    /**
     * Create an instance of {@link Paragraph }
     * 
     */
    public Amandman createAmandman() {
        return new Amandman();
    }
    
    /**
     * Create an instance of {@link Paragraph }
     * 
     */
    public DopunaZakonaAmandamana createDopunaZakonaAmandamana() {
        return new DopunaZakonaAmandamana();
    }
   
    
    /**
     * Create an instance of {@link Paragraph }
     * 
     */
    public SluzbeniListAmandmana createSluzbeniListAmandmana() {
        return new SluzbeniListAmandmana();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/amandmani", name = "Ref")
    public JAXBElement<String> createRef(String value) {
        return new JAXBElement<String>(_Ref_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/amandmani", name = "Label")
    public JAXBElement<String> createLabel(String value) {
        return new JAXBElement<String>(_Label_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.parlament.gov.rs/amandmani", name = "Title")
    public JAXBElement<String> createTitle(String value) {
        return new JAXBElement<String>(_Title_QNAME, String.class, null, value);
    }

}
