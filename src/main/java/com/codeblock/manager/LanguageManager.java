package com.codeblock.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.codeblock.entity.Language;
import com.codeblock.handler.BlogException;
import com.codeblock.handler.ErrorProvider;
import com.codeblock.repository.LanguageRepository;
import com.codeblock.util.Constants;

/**
 * A Manager class to handler Business logic and any other 'Language' DB entity methodology
 * 
 * @author Hoffman
 *
 */
@Service
public class LanguageManager {
	private static final Logger logger = LogManager.getLogger(LanguageManager.class);
	
	private Map<String, String> languageCache ;
	/**
	 * Spring Dependency Injection
	 */
	@Autowired
	private LanguageRepository lanRepository;
	
	/**
	 * Init method will populate the Language cache @see LanguageManager#languageCache
	 */
	public void init() {
		languageCache = new HashMap<String, String>();
		
		for (Language lan : lanRepository.findAll()) {
			languageCache.put(lan.getName(), lan.getImageUrl()) ;
		}
	}
	
	//Get a specific Programming Language for the cache
	public String getProgLanImage(String name) {
		return this.languageCache.get(name) ;
	}
	
	//Get a all Programming Language for the cache
	public Response getAvalibaleLanguages(HttpSession session) {
			return Response.status(200).entity(new HashSet<>(languageCache.keySet())).build();
	}

	/*
	 * Method will retrieve a list of program languages by scraping the web and will
	 * persist it to the 'Language' DB table once its ready
	 */
	public void persistProgLanguages() {
		logger.info("Retrieving programming languages name's and images from the web, This might take a few minutes, Sorry for the inconvenience...");
		try {
			/**
			 * Connect to WIKI website.
			 */
			Document document = Jsoup.connect(Constants.LIST_OF_PROG_LANG).userAgent(
					"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
					.header("Content-Language", "en-US").timeout(15000) //
					.get();

			// Retrieve an {@link Jsoup} element list of all program languages
			Elements LanguagesHrefs = document.select("div[class=div-col columns column-width]").select("a[href]");

			for (Element element : LanguagesHrefs) {
				try {
					String progLangName = element.text();
					/**
					 * Connect to a specific WIKI program language website in order to retrieve the
					 * language image.
					 */

					String targetedUrl = element.attr("href");
					targetedUrl =  "https://en.wikipedia.org"+targetedUrl ;
					try {
						document = Jsoup.connect(targetedUrl).userAgent(
								"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
								.header("Content-Language", "en-US").timeout(15000) //
								.get();
					} catch (Exception e) {
						e.printStackTrace();
					}

					String progLangImageUrl = null;
					// Retrieve an {@link Jsoup} elements list of all program languages url images

					try {
						progLangImageUrl = document.select("tr > td").first().select("a > img").first().attr("abs:src");
					} catch (Exception e) {
						//Do nothing
					}

					//In case WIKI cannot provide the programming language image try to retrieve it from other web source.
					if (StringUtils.isEmpty(progLangImageUrl) || progLangImageUrl.contains("Question_book")) {
						progLangImageUrl = retrieveProgramLanguageImg(progLangName);
					}

					// Save each program language to the language DB entity
					Language progLang = new Language(progLangName, progLangImageUrl) ;
					lanRepository.save(progLang);
					logger.info("A new Language DB Entry: " + progLang);
				} catch (Exception e) {
					continue;
				}
			}
		} catch (Exception e) {
		}

	}

	/*
	 * Method will retrieve an image of program language by scraping the web.
	 */
	public String retrieveProgramLanguageImg(String lang) {
		String languagesImgSrc = Constants.DEFAULT_IMG;
		try {
			/**
			 * Connect to Bing  website.
			 */
			Document document = Jsoup.connect(Constants.PROG_LANG_IMG.replace(Constants.IMG_VAR, lang)).userAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
					.timeout(2000).get();

			// Retrieve a  {@link Jsoup} element list of requested program languages images
			Elements imagesDemo = document.select("img[class=mimg]");
			languagesImgSrc = imagesDemo.get(0).attr("src");
		} catch (Exception e) {
		}
		return languagesImgSrc;
	}

}
