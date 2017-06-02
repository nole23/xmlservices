package com.xml.project.dto;

public class SearchDTO {

	private String docUrl;
	private String docTitle;

	public SearchDTO(String docUrl, String docTitle) {
		super();
		this.docUrl = docUrl;
		this.docTitle = docTitle;
	}

	public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

}
