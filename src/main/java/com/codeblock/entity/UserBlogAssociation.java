package com.codeblock.entity;



import java.io.Serializable;
import java.util.UUID;
/**
 * This Helper POJO-class will be used  to defined the User-Blog-Association in order to help JPA create a Join Column for the USER-BLOG entities in the DB.
 * @author Hoffman
 *
 */
public class UserBlogAssociation implements Serializable {

	private static final long serialVersionUID = -4808978909167200869L;

	private UUID userId;

	private UUID blogId;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blogId == null) ? 0 : blogId.hashCode());
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
		UserBlogAssociation other = (UserBlogAssociation) obj;
		if (blogId == null) {
			if (other.blogId != null)
				return false;
		} else if (!blogId.equals(other.blogId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}





}