package com.xml.project.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class PDFUtil {

	private static DocumentBuilderFactory documentFactory;	
	private static TransformerFactory transformerFactory;
	
	static {
		
		documentFactory = DocumentBuilderFactory.newInstance();
		documentFactory.setNamespaceAware(true);
		documentFactory.setIgnoringComments(true);
		documentFactory.setIgnoringElementContentWhitespace(true);
		
		transformerFactory = TransformerFactory.newInstance();
	}
	
	/*itexpdf*/
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 40,Font.BOLD);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD);
	
	public void generatePDF(File outputFile, com.xml.project.jaxb.Dokument doc) throws FileNotFoundException, DocumentException{
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(outputFile));
		
		document.open();
		Paragraph preface = new Paragraph();
		preface.add(new Paragraph(doc.getNaslov(), catFont));
		preface.add(new Paragraph("Broj lista: "+doc.getSluzbeniList().getBroj_lista(), subFont));
		document.add(preface);
		
		document.newPage();
		
		document.close();
	}
	
public void generateHTML(String xmlPath, String xslPath, String fileOutput) throws FileNotFoundException {
    	
	try {

		// Initialize Transformer instance
		StreamSource transformSource = new StreamSource(new File(xslPath));
		Transformer transformer = transformerFactory.newTransformer(transformSource);
		transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		// Generate XHTML
		transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");

		// Transform DOM to HTML
		DOMSource source = new DOMSource(buildDocument(xmlPath));
		StreamResult result = new StreamResult(new FileOutputStream(fileOutput));
		transformer.transform(source, result);
		
	} catch (TransformerConfigurationException e) {
		e.printStackTrace();
	} catch (TransformerFactoryConfigurationError e) {
		e.printStackTrace();
	} catch (TransformerException e) {
		e.printStackTrace();
	}
    
}

public org.w3c.dom.Document buildDocument(String filePath) {

	org.w3c.dom.Document document = null;
	try {
		
		DocumentBuilder builder = documentFactory.newDocumentBuilder();
		document = builder.parse(new File(filePath)); 

		if (document != null)
			System.out.println("[INFO] File parsed with no errors.");
		else
			System.out.println("[WARN] Document is null.");

	} catch (Exception e) {
		return null;
		
	} 

	return document;
}
}
