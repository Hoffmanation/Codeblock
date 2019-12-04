package com.codearchive.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.codearchive.entity.Blog;
import com.codearchive.entity.Language;
import com.codearchive.entity.User;
import com.codearchive.exce.BlogException;

public interface BlogService {


	public List<Blog> getAllBlogsByLanguage(Language language, UUID userId) throws BlogException;

	public List<Blog> getAllBlogsByUserId(UUID userId) throws BlogException;
	
	public List<Blog> getAllBlogsByDate(String date , UUID userId) throws BlogException;
	
	public List<Blog> SearchAllBlogs(String search , UUID userId) throws BlogException;

	public boolean createBlog(String topic ,String codeBlock , Language language ,String date ,UUID userId) throws BlogException;
	
	public boolean updateBlog(String codeBlock ,String topic,UUID userId)throws BlogException;
	
	public boolean deleteBlog(UUID blogId) throws BlogException;
	
	public List<Blog> getBlogByBlogId(UUID blogId) throws BlogException;

	public Set<Language> getAllLanguagesByUserId(UUID userId) throws BlogException;

}
