package com.xml.project.dto;

import com.xml.project.model.ActCategory;

public class ActCategoryDTO {

	private Long id;
	private String name;
	
	public ActCategoryDTO() {}
	
	public ActCategoryDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public ActCategoryDTO(ActCategory actCategory) {
		this.id = actCategory.getId();
		this.name = actCategory.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ActCategoryDTO [id=" + id + ", name=" + name + "]";
	}
	
	
}
