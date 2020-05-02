package com.codeblock.manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codeblock.entity.Blog;
import com.codeblock.entity.User;
import com.codeblock.handler.BlogException;
import com.codeblock.handler.ErrorProvider;
import com.codeblock.pojo.LoginDetails;
import com.codeblock.pojo.LoginError;
import com.codeblock.pojo.RestMessage;
import com.codeblock.repository.LanguageRepository;
import com.codeblock.service.BlogService;
import com.codeblock.service.SecurityService;
import com.codeblock.service.UserService;
import com.codeblock.util.Constants;
import com.codeblock.util.UserValidator;
import com.google.common.collect.Lists;

/**
 * A service manager class that will handle the Business-Logic for the
 * {@link Blog} DB entity This class serves us as the layer that connect between
 * the REST-API's Layer and the DAO layer of the BLOG entities
 * 
 * @author Hoffman
 *
 */
@Service
public class BlogManager {

	/*
	 * Spring Dependency Injection
	 */
	@Autowired
	private BlogService blogDao;


  //Method will be used to retrieve all Users blog by a given programming language
	public Response getAllBlogsByLanguage(String language, HttpSession session) {
		try {
			User user = (User) session.getAttribute(Constants.USER_LOGIN);
			return Response.status(200).entity(blogDao.getAllBlogsByLanguage(language, user.getUserId())).build();
		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
	}

	  //Method will be used to retrieve all Users blog
	public Response getAllBlogsByUserId(HttpSession session) {
		try {
			User user = (User) session.getAttribute(Constants.USER_LOGIN);
			List<Blog> reversedList = Lists.reverse(blogDao.getAllBlogsByUserId(user.getUserId()));
			return Response.status(200).entity(reversedList).build();
		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
	}

	  //Method will be used to retrieve all Users blog by a giver date
	public Response getAllBlogsByDate(String date, HttpSession session) {
		try {
			User user = (User) session.getAttribute(Constants.USER_LOGIN);
			return Response.status(200).entity(blogDao.getAllBlogsByDate(date, user.getUserId())).build();
		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
	}

	  //Method will be used to retrieve all Users blog by a given search word/s
	public Response searchAllBlogs(String search, HttpSession session) {
		try {
			User user = (User) session.getAttribute(Constants.USER_LOGIN);
			return Response.status(200).entity(blogDao.SearchAllBlogs(search, user.getUserId())).build();
		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
	}
	
	  //Method will be used to retrieve a specific blog by a id
	public Response getBlogByBlogId(UUID blogId) {
		try {
			return Response.status(200).entity(blogDao.getBlogByBlogId(blogId)).build();
		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
	}

	  //Method will be used to retrieve a list of programming Languages by specific user
	public Response getAllLanguagesByUserId(HttpSession session) {
		try {
			User user = (User) session.getAttribute(Constants.USER_LOGIN);
			return Response.status(200).entity(blogDao.getAllLanguagesByUserId(user.getUserId())).build();
		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
	}

	// BLOG CRUD operations methods
	public Response updateBlog(Blog blog, HttpSession session) {
		try {
			blogDao.updateBlog(blog.getCodeblock(), blog.getTopic(), blog.getBlogId());
			return Response.status(200).entity(Status.OK.getReasonPhrase()).build();
		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
	}

	public Response createBlog(Blog blog, HttpSession session) {
		try {
			User user = (User) session.getAttribute(Constants.USER_LOGIN);
			SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM d, yyyy");
			blogDao.createBlog(blog.getTopic(), blog.getCodeblock(), blog.getLanguage(), formatter.format(new Date()),
					user.getUserId());
			return Response.status(201).entity(Status.OK.getReasonPhrase()).build();
		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
	}

	public Response deleteBlog(Blog blog, HttpSession session) {
		try {
			blogDao.deleteBlog(blog.getBlogId());
			return Response.ok(200).entity(new RestMessage("Update seccesfully")).build();
		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
	}







}
