package com.codeblock.util;



import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.codeblock.pojo.LoginDetails;
import com.codeblock.pojo.LoginError;
import com.codeblock.service.UserService;



/**
 * A Helper-Service class for validating user's Form-Param at login or registration
 * @author Hoffman
 *
 */
@Service
public class UserValidator implements Validator {

	/**
	 * Spring Dependency Injection
	 */
	@Autowired
	@Qualifier("messageSourceAccessor")
	private MessageSourceAccessor     messageSourceAccessor;

	@Autowired
	private UserService userService;


	@Override
	public boolean supports(Class<?> aClass) {
		return LoginDetails.class.equals(aClass);
	}


/**
 * Method will validate User's Form-Param and will inject errors (by a given preset of rules) into the {@link BindingResult} Spring-Bean
 */
	@Override
	public void validate(Object o, Errors errors) {

		LoginDetails details = (LoginDetails) o ; 

			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "not.empty");
			if (!CodeblockUtil.emailValidator(details.getUsername())) {
				errors.rejectValue("username", "email.not.valid");

			}
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "not.empty");
			if (StringUtils.isNotEmpty(details.getUsername()) && details.getUsername().length() < 6 || details.getUsername().length() > 30) {
				errors.rejectValue("username", "size.username");
			}

			if (userService.findByUsername(details.getUsername()) != null) {
				errors.rejectValue("passwordConfirm", "duplicate.username");
			}

			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "not.empty");
			if ( StringUtils.isNotEmpty(details.getPassword())  && details.getPassword().length() < 6 || details.getPassword().length() > 30) {
				errors.rejectValue("password", "size.password");
			}

			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "not.empty");
			if ( StringUtils.isNotEmpty(details.getPasswordConfirm())  && !details.getPasswordConfirm().equals(details.getPassword())) {
				errors.rejectValue("passwordConfirm", "dont.match.passwordConfirm");
			}

	}


/**
 * Method will obtain all injected errors from the {@link BindingResult} Spring-Bean and 
 * will inject it into our {@link LoginError} POJO to be presented to the Front-End user.
 * @param bindingResult
 * @return {@link LoginError}
 */
	public  LoginError getError(BindingResult bindingResult) {
		LoginError error = new LoginError() ;
		for (Object object : bindingResult.getAllErrors()) {
		    if(object instanceof FieldError) {
		        FieldError fieldError = (FieldError) object;

		        String errorProperty= fieldError.getCode();
		        String errorMessage = messageSourceAccessor.getMessage(fieldError) ;
		    
		        
				switch (errorProperty) {
				case "not.empty":
					error.setNotEmpty(errorMessage);
					break ;
				case "duplicate.username":
					error.setDuplicateUsername(errorMessage);
					break ;
				case "size.password":
					error.setSizePassword(errorMessage);
					break ;
				case "size.username":
					error.setSizeUsername(errorMessage);
					break ;
				case "dont.match.passwordConfirm":
					error.setDontMatchPasswordConfirm(errorMessage);
					break ;
				case "email.not.valid":
					error.setEmailNotValid(errorMessage);
					break ;
					
				 default:
					 error.setGlobalMessage(errorMessage);
				}
		    }
		}
		return error;
	}

}
