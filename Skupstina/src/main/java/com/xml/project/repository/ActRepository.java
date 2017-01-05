package com.xml.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xml.project.model.Act;

public interface ActRepository extends JpaRepository<Act, Long> {

	Act findByLink(String link);

	void remove(Act art);

}
