package com.codeblock.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * This Entity class will be use to store  'Programming Languages' data   information in the DB
 * @author Hoffman
 *
 */
@Entity
@Table(name = "language")
public class Language {

	private UUID id ;
	private String name ;
	private String imageUrl ;
	
	public Language() {
		// TODO Auto-generated constructor stub
	}

	public Language(String name,String imageUrl) {
		super();
		this.name = name;
		this.imageUrl = imageUrl ;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Language [id=" + id + ", name=" + name + ", imageUrl=" + imageUrl + "]";
	}


	

}
