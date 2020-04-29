package com.codeblock.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * A Helper-Service class with global utility methods for the Codeblock Application
 * @author Hoffman
 *
 */
public abstract class CodeblockUtil{
	private static final Logger logger = LogManager.getLogger(CodeblockUtil.class);
	
	
	/**
	 * Method Will validate if a string is an email
	 * @param email
	 * @return {@link Boolean}
	 */
	public static boolean emailValidator(String email) {
		if (email != "" && email != null) {
			final Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = EMAIL_REGEX.matcher(email);
			return matcher.find();
		} else {
			logger.error("Field email have a null value in it.", new RuntimeException("At least one attributes returned a null value."));
			return false;
		}

	}

}
