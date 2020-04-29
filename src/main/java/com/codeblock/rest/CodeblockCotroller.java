package com.codeblock.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.codeblock.entity.Blog;
import com.codeblock.entity.User;
import com.codeblock.handler.BlogException;
import com.codeblock.handler.ErrorProvider;
import com.codeblock.pojo.Message;
import com.codeblock.repository.LanguageRepository;
import com.codeblock.service.BlogService;
import com.codeblock.util.WebScrapUtil;
import com.google.common.collect.Lists;

@RestController
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Consumes(MediaType.APPLICATION_JSON_VALUE)
public class CodeblockCotroller {

	@Autowired
	private BlogService blogDao;
	@Autowired
	private LanguageRepository languageRepository;

	
	public static final String USER_LOGIN = "user";

	@RequestMapping(path = "codeblock/getAllBlogsByLanguage/{language}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Blog[] getAllBlogsByLanguage(@PathVariable(required = true) String language, HttpSession session)
			throws BlogException {
		Object sessionDetails = session.getAttribute(USER_LOGIN);
		User user = (User) sessionDetails;
		if (!blogDao.getAllBlogsByLanguage(language, user.getUserId()).isEmpty()) {
			return blogDao.getAllBlogsByLanguage(language, user.getUserId()).toArray(new Blog[0]);
		} else {
			throw new BlogException(ErrorProvider.error = "Sorry, we couldn't found a match for your request.");
		}
	}

	@RequestMapping(path = "codeblock/getAllBlogsByUserId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Blog[] getAllBlogsByUserId(HttpSession session) throws BlogException {
		Object sessionDetails = session.getAttribute(USER_LOGIN);
		User user = (User) sessionDetails;
		List<Blog> reversedList = Lists.reverse(blogDao.getAllBlogsByUserId(user.getUserId())); 
		return reversedList.toArray(new Blog[0]);
	
	}

	@RequestMapping(path = "codeblock/getAllBlogsByDate/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Blog[] getAllBlogsByDate(@PathVariable(required = true) String date, HttpSession session) throws BlogException {
		Object sessionDetails = session.getAttribute(USER_LOGIN);
		User user = (User) sessionDetails;
		if (!blogDao.getAllBlogsByDate(date, user.getUserId()).isEmpty()) {
			return blogDao.getAllBlogsByDate(date, user.getUserId()).toArray(new Blog[0]);
		} else {
			throw new BlogException(ErrorProvider.error = "Sorry, we couldn't found a match for your request.");
		}
	}

	@RequestMapping(path = "codeblock/SearchAllBlogs/{search}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Blog[] SearchAllBlogs(@PathVariable(required = true) String search, HttpSession session) throws BlogException {
		Object sessionDetails = session.getAttribute(USER_LOGIN);
		User user = (User) sessionDetails;
		if (!blogDao.SearchAllBlogs(search, user.getUserId()).isEmpty()) {
			return blogDao.SearchAllBlogs(search, user.getUserId()).toArray(new Blog[0]);
		} else {
			throw new BlogException(ErrorProvider.error = "Sorry, we couldn't found a match for your request.");
		}
	}

	@RequestMapping(path = "codeblock/updateBlog/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean updateBlog(@RequestBody(required = true) Blog blog,HttpSession session) throws BlogException {
		if (blog.getCodeblock() != null) {
			blogDao.updateBlog(blog.getCodeblock(), blog.getTopic(), blog.getBlogId());
			return true;
		} else {
			throw new BlogException(ErrorProvider.error = "Updating blog has failed..");
		}
	}


	@RequestMapping(path = "codeblock/createBlog/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean createBlog(@RequestBody(required = true) Blog blog, HttpSession session) throws BlogException {
		Object sessionDetails = session.getAttribute(USER_LOGIN);
		User user = (User) sessionDetails;
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM d, yyyy");
		if (blog.getTopic() != null && blog.getCodeblock() != null) {
			blogDao.createBlog(blog.getTopic(), blog.getCodeblock(), blog.getLanguage(), formatter.format(new Date()),
					user.getUserId());
			System.out.println(user.getUserId());
			return true;
		} else {
			throw new BlogException(ErrorProvider.error = "please fill out all required fields. ");
		}
	}

	@RequestMapping(path = "codeblock/deleteBlog/", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response deleteBlog(@RequestBody(required = true) Blog blog, HttpSession session) throws BlogException {
		blogDao.deleteBlog(blog.getBlogId());
		return Response.ok(200).entity(new Message("Update seccesfully")).build();
	}

	@RequestMapping(path = "codeblock/getBlogByBlogId/{blogId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Blog[] getBlogByBlogId(@PathVariable(required = true) UUID blogId, @Context HttpServletRequest request,
			@Context HttpServletResponse response) throws BlogException {
		if (!blogDao.getBlogByBlogId(blogId).isEmpty()) {
			return blogDao.getBlogByBlogId(blogId).toArray(new Blog[0]);
		} else {
			throw new BlogException(ErrorProvider.error = "Sorry, we couldn't found a match for your request.");
		}
	}

	@RequestMapping(path = "codeblock/getAllLanguagesByUserId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String[] getAllLanguagesByUserId(HttpSession session) throws BlogException {
		Object sessionDetails = session.getAttribute(USER_LOGIN);
		User user = (User) sessionDetails;
			Set<String> len = blogDao.getAllLanguagesByUserId(user.getUserId());
			return len.toArray(new String[0]);
	}

	@RequestMapping(path = "codeblock/getAvalibaleLanguages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String[] getAvalibaleLanguages(HttpSession session) throws BlogException {
		try {
			return languageRepository.getAllLanguages().toArray(new String[0]);
		} catch (Exception e) {
			throw new BlogException(ErrorProvider.error = "There are no lenguages found for this user in any blog.");
		}
	}

	@RequestMapping(path = "codeblock/getInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Message getInfo(HttpSession session) throws BlogException {
		User user = (User) session.getAttribute(USER_LOGIN);
		return new Message(user.getUsername());
	}

	@RequestMapping(path = "codeblock/getId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Message getId(HttpSession session) throws BlogException {
		User user = (User) session.getAttribute(USER_LOGIN);
		return new Message(user.getUserId().toString());
	}
	

	@RequestMapping(path = "codeblock/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean logout(@Context HttpServletRequest request,HttpSession session) throws BlogException {
	    session = request.getSession(false);
		session.invalidate();
		if (session != null) {
			session = null;
		}
		return true;
	}

}
