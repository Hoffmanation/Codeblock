package com.codeblock.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * This Entity class will be used as a User-Blog-Association and will create a Join Column for the USER-BLOG entities in the DB.
 * @author Hoffman
 *
 */
@Entity
@Table(name = "USER_BLOG")
@IdClass(UserBlogAssociation.class)
public class UserBlog implements Serializable {
	private static final long serialVersionUID = 2356940831665710978L;

	@Id
	private UUID userId;

	@Id
	private UUID blogId;

	@ManyToOne
	@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@ManyToOne
	private Blog blog;

}