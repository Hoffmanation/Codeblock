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
import javax.ws.rs.core.Response.Status;

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
import com.codeblock.manager.CodeblockAppManager;
import com.codeblock.pojo.Message;
import com.codeblock.repository.LanguageRepository;
import com.codeblock.service.BlogService;
import com.codeblock.util.WebScrapUtil;
import com.google.common.collect.Lists;

@RestController
public class CodeblockCotroller {

	@Autowired
	private CodeblockAppManager appManager ;


	@RequestMapping(path = "codeblock/getAllBlogsByLanguage/{language}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response getAllBlogsByLanguage(@PathVariable(required = true) String language, HttpSession session) {
		return appManager.getAllBlogsByLanguage(language, session);
	}

	@RequestMapping(path = "codeblock/getAllBlogsByUserId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response getAllBlogsByUserId(HttpSession session)  {
		return appManager.getAllBlogsByUserId(session);
	}

	@RequestMapping(path = "codeblock/getAllBlogsByDate/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response getAllBlogsByDate(@PathVariable(required = true) String date, HttpSession session)  {
		return appManager.getAllBlogsByDate(date, session);
	}

	@RequestMapping(path = "codeblock/SearchAllBlogs/{search}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response searchAllBlogs(@PathVariable(required = true) String search, HttpSession session)  {
		return appManager.searchAllBlogs(search, session);
	}

	@RequestMapping(path = "codeblock/updateBlog/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response updateBlog(@RequestBody(required = true) Blog blog,HttpSession session)  {
		return appManager.updateBlog(blog, session);
	}


	@RequestMapping(path = "codeblock/createBlog/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response createBlog(@RequestBody(required = true) Blog blog, HttpSession session)  {
		return appManager.createBlog(blog, session);
	}

	@RequestMapping(path = "codeblock/deleteBlog/", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response deleteBlog(@RequestBody(required = true) Blog blog, HttpSession session)  {
		return appManager.deleteBlog(blog, session);
	}

	@RequestMapping(path = "codeblock/getBlogByBlogId/{blogId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response getBlogByBlogId(@PathVariable(required = true) UUID blogId, @Context HttpServletRequest request) {
			return appManager.getBlogByBlogId(blogId);
		}

	@RequestMapping(path = "codeblock/getAllLanguagesByUserId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response  getAllLanguagesByUserId(HttpSession session)  {
		return appManager.getAllLanguagesByUserId(session);
	}

	@RequestMapping(path = "codeblock/getAvalibaleLanguages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response getAvalibaleLanguages(HttpSession session)  {
		return appManager.getAvalibaleLanguages(session);
	}

	@RequestMapping(path = "codeblock/getUserInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response getUserInfo(HttpSession session)  {
		return appManager.getUserInfo(session);
	}


	@RequestMapping(path = "codeblock/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean logout(@Context HttpServletRequest request,HttpSession session)  {
	    session = request.getSession(false);
		session.invalidate();
		if (session != null) {
			session = null;
		}
		return true;
	}

}
