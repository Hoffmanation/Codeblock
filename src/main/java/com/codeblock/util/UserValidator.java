package com.codeblock.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.codeblock.dao.UserService;
import com.codeblock.entity.User;

@Component
@PropertySource("classpath:validation.properties")
public class UserValidator implements Validator {

	@Value("${NotEmpty}")
	private String notEmpty;
	@Value("${Size.userForm.username}")
	private String usernameLength;
	@Value("${Duplicate.userForm.username}")
	private String usernameDuplicate;
	@Value("${Size.userForm.password}")
	private String passwordLength;
	@Value("${Diff.userForm.passwordConfirm}")
	private String passwordNotMatch;

	public static List<String> appErrors;
	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		User user = (User) o;
		appErrors = new ArrayList<>();
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
		if (user.getUsername().length() < 6 || user.getUsername().length() > 30) {
			errors.rejectValue("username", "Size.userForm.username");
			appErrors.add(usernameLength);
		}
		if (userService.findByUsername(user.getUsername()) != null) {
			errors.rejectValue("username", "Duplicate.userForm.username");
			appErrors.add(usernameDuplicate);
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		if (user.getPassword().length() < 6 || user.getPassword().length() > 30) {
			errors.rejectValue("password", "Size.userForm.password");
			appErrors.add( passwordLength);
		}

		if (!user.getPasswordConfirm().equals(user.getPassword())) {
			errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
			appErrors.add( passwordNotMatch);
		}
	}

	public static  List<String> getError() {
		return appErrors;
	}
}
