package com.codeblock.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * This Entity class will be use to store upload users blog information in the DB
 * @author Hoffman
 *
 */
@Entity
@Table(name = "blog")
public class Blog implements Serializable {
	private static final long serialVersionUID = 1822944677883779213L;

	private String topic;
	@Lob
	@Type(type = "org.hibernate.type.TextType")
	private String codeblock;
	private String language;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "blog_id")
	private UUID blogId;
	private String date;
	private String img;
	private UUID userId;

	public Blog() {
	}

	public Blog(String topic, String codeblock, String language, String date, String img, UUID userId) {
		super();
		this.topic = topic;
		this.codeblock = codeblock;
		this.language = language;
		this.date = date;
		this.img = img;
		this.userId = userId;
	}

	public Blog(String codeblock) {
		super();
		this.codeblock = codeblock;
	}

	public UUID getBlogId() {
		return blogId;
	}

	public void setBlogId(UUID blogId) {
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

	public String getCodeblock() {
		return codeblock;
	}

	public void setCodeblock(String codeblock) {
		this.codeblock = codeblock;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
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
		return "Blog [topic=" + topic + ", codeblock=" + codeblock + ", language=" + language + ", blogId=" + blogId
				+ ", date=" + date + ", img=" + img + ", userId=" + userId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blogId == null) ? 0 : blogId.hashCode());
		result = prime * result + ((codeblock == null) ? 0 : codeblock.hashCode());
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
		if (codeblock == null) {
			if (other.codeblock != null)
				return false;
		} else if (!codeblock.equals(other.codeblock))
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
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
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
