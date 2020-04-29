package com.codeblock.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.codeblock.entity.Blog;
import com.codeblock.entity.Language;


@RepositoryRestResource(path = "/Language", collectionResourceRel = "Language")
public interface LanguageRepository extends JpaRepository<Language,UUID> {

	
	@RestResource(exported = false)
	@Query("SELECT l.name FROM Language AS l")
	public List<String> getAllLanguages();
	

}
