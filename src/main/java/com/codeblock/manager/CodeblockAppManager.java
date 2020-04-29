package com.codeblock.manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeblock.entity.Blog;
import com.codeblock.entity.User;
import com.codeblock.handler.BlogException;
import com.codeblock.handler.ErrorProvider;
import com.codeblock.pojo.LoginDetails;
import com.codeblock.pojo.LoginError;
import com.codeblock.pojo.Message;
import com.codeblock.repository.LanguageRepository;
import com.codeblock.service.BlogService;
import com.codeblock.service.SecurityService;
import com.codeblock.service.UserService;
import com.codeblock.util.Constants;
import com.codeblock.util.UserValidator;
import com.google.common.collect.Lists;

/**
 * A service manager class that will handle the Business-Logic for {@link User}
 * , {@link Blog} and their associations This class serves us as the layer that
 * connect between the REST-API's Layer and the DAO layer of the USER-BLOG
 * entities
 * 
 * @author Hoffman
 *
 */
@Service
public class CodeblockAppManager {

	/*
	 * Spring Dependency Injection
	 */
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private UserValidator userValidator;
	@Autowired
	private BlogService blogDao;
	@Autowired
	private LanguageRepository languageRepository;

	/**
	 * Registration B-L method
	 * 
	 * @param userDetail
	 * @param bindingResult
	 * @param session
	 * @return {@link Response}
	 */
	public Response registration(LoginDetails userDetail, BindingResult bindingResult, HttpSession session) {
		try {
			User user = new User(userDetail.getUsername(), userDetail.getPassword());
			userValidator.validate(userDetail, bindingResult);
			if (bindingResult.hasErrors()) {
				LoginError errors = userValidator.getError(bindingResult);
				return Response.status(401).entity(errors).build();
			}
			userService.createUser(new User(userDetail.getUsername(), userDetail.getPassword()));
			session.setAttribute(Constants.USER_LOGIN, user);
			login(userDetail, session);
		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
		return Response.status(200).entity(Status.OK.getReasonPhrase()).build();
	}

	/**
	 * Login B-L method
	 * 
	 * @param userDetail
	 * @param session
	 * @return {@link Response}
	 */
	public Response login(LoginDetails userDetail, HttpSession session) {
		try {
			if (securityService.autologin(userDetail.getUsername(), userDetail.getPassword())) {
				User user = userService.findByUsername(userDetail.getUsername());
				session.setAttribute(Constants.USER_LOGIN, user);
				return Response.status(200).entity(Status.OK.getReasonPhrase()).build();
			}
			return Response.status(401).entity("*Username or password are incorrect.").build();

		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
	}


	public Response getAllBlogsByLanguage(String language, HttpSession session) {
		try {
			User user = (User) session.getAttribute(Constants.USER_LOGIN);
			return Response.status(200).entity(blogDao.getAllBlogsByLanguage(language, user.getUserId())).build();
		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
	}

	public Response getAllBlogsByUserId(HttpSession session) {
		try {
			User user = (User) session.getAttribute(Constants.USER_LOGIN);
		List<Blog> reversedList = Lists.reverse(blogDao.getAllBlogsByUserId(user.getUserId()));
		return Response.status(200).entity(reversedList).build();
	} catch (BlogException e) {
		return new ErrorProvider().toResponse(e);
	}
}

	public Response getAllBlogsByDate(String date, HttpSession session) {
		try {
			User user = (User) session.getAttribute(Constants.USER_LOGIN);
			return Response.status(200).entity(blogDao.getAllBlogsByDate(date, user.getUserId())).build();
		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
	}

	public Response searchAllBlogs(String search, HttpSession session) {
		try {
			User user = (User) session.getAttribute(Constants.USER_LOGIN);
			return Response.status(200).entity(blogDao.SearchAllBlogs(search, user.getUserId())).build();
		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
	}


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
		User user = (User) session.getAttribute(Constants.USER_LOGIN);;
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM d, yyyy");
		blogDao.createBlog(blog.getTopic(), blog.getCodeblock(), blog.getLanguage(), formatter.format(new Date()),user.getUserId());
		return Response.status(201).entity(Status.OK.getReasonPhrase()).build();
		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
	}

	public Response deleteBlog(Blog blog, HttpSession session) {
		try {
			blogDao.deleteBlog(blog.getBlogId());
			return Response.ok(200).entity(new Message("Update seccesfully")).build();
		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
	}

	public Response getBlogByBlogId(UUID blogId) {
		try {
			return Response.status(200).entity(blogDao.getBlogByBlogId(blogId)).build();
		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
	}

	public Response getAllLanguagesByUserId(HttpSession session) {
		try {
			User user = (User) session.getAttribute(Constants.USER_LOGIN);
			return Response.status(200).entity(blogDao.getAllLanguagesByUserId(user.getUserId())).build();
		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
	}

	public Response getAvalibaleLanguages(HttpSession session) {
		try {
			return Response.status(200).entity(languageRepository.getAllLanguages()).build();
		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
	}

	/**
	 * Get and display the user info B-L method
	 * 
	 * @param session
	 * @return {@link Response}
	 */
	public Response getUserInfo(HttpSession session)  {
		User user = (User) session.getAttribute(Constants.USER_LOGIN);
		return Response.status(200).entity(user).build();
}

}
