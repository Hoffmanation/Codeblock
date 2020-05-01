package com.codeblock.app;



import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.codeblock.manager.LnguageManager;
import com.codeblock.repository.LanguageRepository;
import com.codeblock.util.Constants;

/**
 * A Spring Context listener class that will initialized right after the embedded tomcat
 * container is fully running and ready to serve HTTP requests and the spring application context was fully
 * initialized and ready to serve all Spring-Beans.
 * 
 * @author Hoffman
 *
 */
@Component
public class CodeblockSpringContextListener implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger logger = LogManager.getLogger(CodeblockSpringContextListener.class);

	
	@Autowired
	private LnguageManager lanManager ;
	
	@Autowired
	private LanguageRepository lanRepository;

	/**
	 * Method will be called right after application startup and after spring {@link ApplicationContext} is ready
	 * to serve any {@link Bean} requested
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		long rowCount = lanRepository.getRowCount();
		if (rowCount <= 0) {
			logger.info("Attempting to scrap the web to retrieve a List of programming languages names ans images, Targeted url : "+ Constants.LIST_OF_PROG_LANG);
			// Scrap wiki and retrieve List of programming languages for DB Persistence
			lanManager.persistProgLanguages();
		}
		lanManager.init();
		logger.info("Codeblock application is up and running!");

	}


}
