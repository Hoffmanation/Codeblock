package com.codearchive.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "USER_BLOG")
@IdClass(UserBlogAccociation.class)
public class User_Blogs implements Serializable {
	private static final long serialVersionUID = 2356940831665710978L;

	@Id
	private UUID userId;

	@Id
	private UUID blogId;

	@ManyToOne
	@PrimaryKeyJoinColumn(name = "USER_ID", referencedColumnName = "ID")
	private User user;

	@ManyToOne
	private Blog blog;

}