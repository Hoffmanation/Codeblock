package com.codeblock.rest;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import com.codeblock.manager.BlogManager;
import com.codeblock.manager.LanguageManager;

/**
 * A Collection of {@link RestController} class that will accept HTTP request to
 * interact with the application JS client, A Collection of REST-API's for the 
 * {@link Blog} entity B-L and CRUD operations,
 * 
 * @author Hoffman
 *
 */
@RestController
public class CodeblockCotroller {

	@Autowired
	private BlogManager appManager ;
	@Autowired
	private LanguageManager  lanManager ;

	@RequestMapping(path = "codeblock/createBlog/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response createBlog(@RequestBody(required = true) Blog blog, HttpSession session)  {
		return appManager.createBlog(blog, session);
	}
	
	@RequestMapping(path = "codeblock/deleteBlog/", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response deleteBlog(@RequestBody(required = true) Blog blog, HttpSession session)  {
		return appManager.deleteBlog(blog, session);
	}
	
	
	@RequestMapping(path = "codeblock/updateBlog/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response updateBlog(@RequestBody(required = true) Blog blog,HttpSession session)  {
		return appManager.updateBlog(blog, session);
	}

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
		return lanManager.getAvalibaleLanguages(session);
	}



}
