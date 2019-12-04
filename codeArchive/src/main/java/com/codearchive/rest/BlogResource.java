package com.codearchive.rest;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codearchive.dao.BlogService;
import com.codearchive.daoImpl.SecurityServiceImpl;
import com.codearchive.daoImpl.UserDetailsServiceImpl;
import com.codearchive.entity.Blog;
import com.codearchive.entity.Language;
import com.codearchive.entity.Message;
import com.codearchive.entity.User;
import com.codearchive.exce.BlogException;
import com.codearchive.exce.ErrorProvider;
import com.google.common.collect.Lists;

@RestController
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Consumes(MediaType.APPLICATION_JSON_VALUE)
public class BlogResource {

	@Autowired
	private BlogService blogDao;
	@Autowired
	private UserDetailsServiceImpl service;
	public static final String USER_LOGIN = "user";

	@RequestMapping(path = "codeBlock/getAllBlogsByLanguage/{language}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Blog[] getAllBlogsByLanguage(@PathVariable(required = true) String language, HttpSession session)
			throws BlogException {
		Object sessionDetails = session.getAttribute(USER_LOGIN);
		User user = (User) sessionDetails;
		if (!blogDao.getAllBlogsByLanguage(Language.valueOf(language), user.getUserId()).isEmpty()) {
			return blogDao.getAllBlogsByLanguage(Language.valueOf(language), user.getUserId()).toArray(new Blog[0]);
		} else {
			throw new BlogException(ErrorProvider.error = "Sorry, we couldn't found a match for your request.");
		}
	}

	@RequestMapping(path = "codeBlock/getAllBlogsByUserId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Blog[] getAllBlogsByUserId(HttpSession session) throws BlogException {
		Object sessionDetails = session.getAttribute(USER_LOGIN);
		User user = (User) sessionDetails;
		List<Blog> reversedList = Lists.reverse(blogDao.getAllBlogsByUserId(user.getUserId())); 
		return reversedList.toArray(new Blog[0]);
	
	}

	@RequestMapping(path = "codeBlock/getAllBlogsByDate/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Blog[] getAllBlogsByDate(@PathVariable(required = true) String date, HttpSession session) throws BlogException {
		Object sessionDetails = session.getAttribute(USER_LOGIN);
		User user = (User) sessionDetails;
		if (!blogDao.getAllBlogsByDate(date, user.getUserId()).isEmpty()) {
			return blogDao.getAllBlogsByDate(date, user.getUserId()).toArray(new Blog[0]);
		} else {
			throw new BlogException(ErrorProvider.error = "Sorry, we couldn't found a match for your request.");
		}
	}

	@RequestMapping(path = "codeBlock/SearchAllBlogs/{search}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Blog[] SearchAllBlogs(@PathVariable(required = true) String search, HttpSession session) throws BlogException {
		Object sessionDetails = session.getAttribute(USER_LOGIN);
		User user = (User) sessionDetails;
		if (!blogDao.SearchAllBlogs(search, user.getUserId()).isEmpty()) {
			return blogDao.SearchAllBlogs(search, user.getUserId()).toArray(new Blog[0]);
		} else {
			throw new BlogException(ErrorProvider.error = "Sorry, we couldn't found a match for your request.");
		}
	}

	@RequestMapping(path = "codeBlock/updateBlog/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean updateBlog(@RequestBody(required = true) Blog blog,HttpSession session) throws BlogException {
		if (blog.getCodeBlock() != null) {
			blogDao.updateBlog(blog.getCodeBlock(), blog.getTopic(), blog.getId());
			return true;
		} else {
			throw new BlogException(ErrorProvider.error = "Updating blog has failed..");
		}
	}

	// need to be fixd
	@RequestMapping(path = "codeBlock/createBlog/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean createBlog(@RequestBody(required = true) Blog blog, HttpSession session) throws BlogException {
		Object sessionDetails = session.getAttribute(USER_LOGIN);
		User user = (User) sessionDetails;
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM d, yyyy");
		if (blog.getTopic() != null && blog.getCodeBlock() != null) {
			blogDao.createBlog(blog.getTopic(), blog.getCodeBlock(), blog.getLanguage(), formatter.format(new Date()),
					user.getUserId());
			System.out.println(user.getUserId());
			return true;
		} else {
			throw new BlogException(ErrorProvider.error = "please fill out all required fields. ");
		}
	}

	@RequestMapping(path = "codeBlock/deleteBlog/", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response deleteBlog(@RequestBody(required = true) Blog blog, HttpSession session) throws BlogException {
		blogDao.deleteBlog(blog.getId());
		return Response.ok(200).entity(new Message("Update seccesfully")).build();
	}

	@RequestMapping(path = "codeBlock/getBlogByBlogId/{blogId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Blog[] getBlogByBlogId(@PathVariable(required = true) UUID blogId, @Context HttpServletRequest request,
			@Context HttpServletResponse response) throws BlogException {
		if (!blogDao.getBlogByBlogId(blogId).isEmpty()) {
			return blogDao.getBlogByBlogId(blogId).toArray(new Blog[0]);
		} else {
			throw new BlogException(ErrorProvider.error = "Sorry, we couldn't found a match for your request.");
		}
	}

	@RequestMapping(path = "codeBlock/getAllLanguagesByUserId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String[] getAllLanguagesByUserId(HttpSession session) throws BlogException {
		Object sessionDetails = session.getAttribute(USER_LOGIN);
		User user = (User) sessionDetails;
			Set<Language> len = blogDao.getAllLanguagesByUserId(user.getUserId());
			Set<String> newLen = new HashSet<>();
			for (Language obj : len) {
				newLen.add(obj.toString());
			}
			return newLen.toArray(new String[0]);
	}

	@RequestMapping(path = "codeBlock/getAvalibaleLanguages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String[] getAvalibaleLanguages(HttpSession session) throws BlogException {
		try {
			return Arrays.toString(Language.values()).replaceAll("^.|.$", "").split(", ");
		} catch (Exception e) {
			throw new BlogException(ErrorProvider.error = "There are no lenguages found for this user in any blog.");
		}
	}

	@RequestMapping(path = "codeBlock/getInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Message getInfo(HttpSession session) throws BlogException {
		Object sessionDetails = session.getAttribute(USER_LOGIN);
		User user = (User) sessionDetails;
		return new Message(user.getUsername());
	}

	@RequestMapping(path = "codeBlock/getId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Message getId(HttpSession session) throws BlogException {
		Object sessionDetails = session.getAttribute(USER_LOGIN);
		User user = (User) sessionDetails;
		return new Message(user.getUserId().toString());
	}

	@RequestMapping(path = "codeBlock/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean logout(@Context HttpServletRequest request,HttpSession session) throws BlogException {
	    session = request.getSession(false);
		session.invalidate();
		if (session != null) {
			session = null;
		}
		return true;
	}

}
