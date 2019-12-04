package com.codearchive.dao;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.codearchive.entity.Blog;
import com.codearchive.entity.Language;


@RepositoryRestResource(path = "/Blog", collectionResourceRel = "Blog")
public interface BlogRepository extends JpaRepository<Blog,Long> {
	
	@RestResource(exported = false)
	@Query("SELECT b FROM Blog  AS b WHERE b.language = :language AND b.userId = :userId")
	public List<Blog> getAllBlogsByLanguage(@Param("language") Language language, @Param("userId") UUID userId);

	@RestResource(exported = false)
	@Query("SELECT b FROM Blog  AS b WHERE b.userId = :userId ORDER BY b.date DESC")
	public List<Blog> getAllBlogsByUserId(@Param("userId") UUID userId);
	
	@RestResource(exported = false)
	@Query("SELECT b FROM Blog  AS b WHERE b.date = :date AND b.userId = :userId")
	public List<Blog> getAllBlogsByDate(@Param("date") String date ,@Param("userId")  UUID userId);
	
	@RestResource(exported = false)
	@Query("SELECT b FROM Blog  AS b WHERE UPPER(b.codeBlock) LIKE %:codeBlock% AND b.userId = :userId")
	public List<Blog> SearchAllBlogs(@Param("codeBlock") String codeBlock , @Param("userId") UUID userId);
	
	@RestResource(exported = false)
	@Query("SELECT b FROM Blog  AS b WHERE b.blogId = :blogId")
	public List<Blog> getBlogByBlogId(@Param("blogId") UUID blogId);

	@RestResource(exported = false)
	@Query("SELECT b.language FROM Blog  AS b WHERE b.userId = :userId")
	public Set<Language> getAllLanguagesByUserId(@Param("userId") UUID userId);
	
	@Modifying
	@Query("update Blog b set b.codeBlock= :codeBlock , b.topic= :topic where b.blogId = :blogId")
	public void updateBlog(@Param("codeBlock") String codeBlock, @Param("topic") String topic,@Param("blogId") UUID blogId);

}
