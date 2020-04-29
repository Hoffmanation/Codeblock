package com.codeblock.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codeblock.entity.Language;
import com.codeblock.repository.LanguageRepository;

/**
 * A Utility component for scraping the web via {@link Jsoup} which contains a
 * helper method mainly for the 'Language' DB entity
 * 
 * @author Hoffman
 *
 */
@Component
public class WebScrapUtil {
	private static final Logger logger = LogManager.getLogger(WebScrapUtil.class);
	/**
	 * Spring Dependency Injection
	 */
	@Autowired
	private LanguageRepository lanRepository ;
	/*
	 * Method will retrieve a list of program languages by scraping the web and will
	 * persist it to the 'Language' DB table once its ready
	 */
	public void persistProgLanguages() {
		try {
			/**
			 * Connect to WIKI website.
			 */
			Document document = Jsoup.connect(Constants.LIST_OF_PROG_LANG).userAgent(
					"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
					.header("Content-Language", "en-US").timeout(15000) //
					.get();
			
			 //Retrieve an {@link Jsoup}  element list of all program languages
			Elements LanguagesHrefs = document.select("div[class=div-col columns column-width]").select("a[href]");

			for (Element element : LanguagesHrefs) {
				//Save each program language to the language DB entity
				lanRepository.save(new Language(element.text())) ;
			}
		} catch (Exception e) {
			logger.error("An error has occurred while trying to  retrieve a List of programming languages", e);
		}

	}
	
	/*
	 * Method will retrieve an image of program language by scraping the web.
	 */
	public String retrieveProgramLanguageImg(String lang) {
		String languagesImgSrc = Constants.DEFAULT_IMG ;
		try {
			/**
			 * Connect to WIKI website.
			 */
			Document document = Jsoup.connect(Constants.PROG_LANG_IMG.replace(Constants.IMG_VAR, lang))
				     .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").timeout(2000) 
				     .get() ;
			
			//Retrieve an {@link Jsoup}  element list of requested  program languages images
			Elements  imagesDemo=  document.select("img[class=mimg]");
			languagesImgSrc =  imagesDemo.get(0).attr("src") ;
		} catch (Exception e) {
			logger.error("An error has occurred while trying to  retrieve a List of programming languages", e);
		}
		return languagesImgSrc ;
	}

}
