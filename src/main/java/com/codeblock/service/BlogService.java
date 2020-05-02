package com.codeblock.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.codeblock.entity.Blog;
import com.codeblock.handler.BlogException;
/**
 * An Interface  for the {@link Blog} DAO-Layer service
 * @author Hoffman
 *
 */
public interface BlogService {


	public List<Blog> getAllBlogsByLanguage(String language, UUID userId) throws BlogException;

	public List<Blog> getAllBlogsByUserId(UUID userId) throws BlogException;
	
	public List<Blog> getAllBlogsByDate(String date , UUID userId) throws BlogException;
	
	public List<Blog> SearchAllBlogs(String search , UUID userId) throws BlogException;

	public boolean createBlog(String topic ,String codeblock , String language ,String date ,UUID userId) throws BlogException;
	
	public boolean updateBlog(String codeblock  ,String topic,UUID userId)throws BlogException;
	
	public boolean deleteBlog(UUID blogId) throws BlogException;
	
	public List<Blog> getBlogByBlogId(UUID blogId) throws BlogException;

	public Set<String> getAllLanguagesByUserId(UUID userId) throws BlogException;

}
