package com.codearchive.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="BLOG")
public class Blog implements Serializable {
	private static final long serialVersionUID = -5L;

	private String topic;
	@Lob
	@Type(type = "org.hibernate.type.TextType")
	private String codeBlock;
	@Enumerated(EnumType.STRING)
	private Language language;
	@Id
	@Column(name = "BLOG_ID")
	private UUID blogId= UUID.randomUUID();
	private String date;
	private String img ;  
	private UUID userId ; 
//	private Blob img ;

	
	public Blog() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Blog(String topic, String codeBlock, Language language, String date, String img,UUID userId) {
		super();
		this.topic = topic;
		this.codeBlock = codeBlock;
		this.language = language;
		this.date = date;
		this.img = img;
		this.userId = userId;
	}

	


	public Blog(String codeBlock, UUID blogId) {
		super();
		this.codeBlock = codeBlock;
		this.blogId = blogId;
	}


	public UUID getId() {
		return blogId;
	}

	public void setId(UUID blogId) {
		this.blogId = blogId;
	}
	
	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}


	public String getCodeBlock() {
		return codeBlock;
	}

	public void setCodeBlock(String codeBlock) {
		this.codeBlock = codeBlock;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}


	public String getImg() {
		return img;
	}


	public void setImg(String img) {
		this.img = img;
	}


	@Override
	public String toString() {
		return "Blog [topic=" + topic + ", codeBlock=" + codeBlock + ", language=" + language + ", blogId=" + blogId
				+ ", date=" + date + ", img=" + img + ", userId=" + userId + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blogId == null) ? 0 : blogId.hashCode());
		result = prime * result + ((codeBlock == null) ? 0 : codeBlock.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((img == null) ? 0 : img.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((topic == null) ? 0 : topic.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Blog other = (Blog) obj;
		if (blogId == null) {
			if (other.blogId != null)
				return false;
		} else if (!blogId.equals(other.blogId))
			return false;
		if (codeBlock == null) {
			if (other.codeBlock != null)
				return false;
		} else if (!codeBlock.equals(other.codeBlock))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (img == null) {
			if (other.img != null)
				return false;
		} else if (!img.equals(other.img))
			return false;
		if (language != other.language)
			return false;
		if (topic == null) {
			if (other.topic != null)
				return false;
		} else if (!topic.equals(other.topic))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}





	
}
