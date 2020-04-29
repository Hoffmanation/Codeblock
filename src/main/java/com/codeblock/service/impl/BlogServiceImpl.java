package com.codeblock.service.impl;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codeblock.entity.Blog;
import com.codeblock.handler.BlogException;
import com.codeblock.repository.BlogRepository;
import com.codeblock.service.BlogService;
import com.codeblock.util.WebScrapUtil;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogRepository blogDao;
	
	@Autowired
	private WebScrapUtil  webScrapUtil;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Blog> getAllBlogsByLanguage(String language, UUID userId) throws BlogException {
		try {
			return blogDao.getAllBlogsByLanguage(language, userId);
		} catch (Exception e) {
			throw new BlogException(e.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Blog> getAllBlogsByUserId(UUID userId) throws BlogException {
		try {
			return blogDao.getAllBlogsByUserId(userId);
		} catch (Exception e) {
			throw new BlogException(e.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Blog> getAllBlogsByDate(String date, UUID userId) throws BlogException {
		try {
			return blogDao.getAllBlogsByDate(date, userId);
		} catch (Exception e) {
			throw new BlogException(e.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Blog> SearchAllBlogs(String search, UUID userId) throws BlogException {
		try {
			return blogDao.SearchAllBlogs(search, userId);
		} catch (Exception e) {
			throw new BlogException(e.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean createBlog(String topic, String codeblock, String language, String date, UUID userId)
			throws BlogException {
		try {
			Blog blog = new Blog(topic, codeblock, language.toUpperCase(), date,webScrapUtil.retrieveProgramLanguageImg(language), userId);
			blogDao.save(blog);
			return true;
		} catch (Exception e) {
			throw new BlogException(e.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean updateBlog(String codeblock, String topic, UUID blogId) throws BlogException {
		try {
			blogDao.updateBlog(codeblock, topic,blogId);
			return true;
		} catch (Exception e) {
			throw new BlogException(e.getMessage());
		}
	}

	@Override
	public boolean deleteBlog(UUID blogId) throws BlogException {
		try {
			List<Blog> tempBlog = blogDao.getBlogByBlogId(blogId);
			blogDao.delete(tempBlog.get(0));
			return true;
		} catch (Exception e) {
			throw new BlogException(e.getMessage());
		}
	}

	@Override
	public List<Blog> getBlogByBlogId(UUID blogId) throws BlogException {
		try {
			return blogDao.getBlogByBlogId(blogId);
		} catch (Exception e) {
			throw new BlogException(e.getMessage());
		}
	}

	@Override
	public Set<String> getAllLanguagesByUserId(UUID userId) throws BlogException {
		try {
			return blogDao.getAllLanguagesByUserId(userId) ;
		} catch (Exception e) {
			throw new BlogException(e.getMessage()) ;
		}
	}


}
