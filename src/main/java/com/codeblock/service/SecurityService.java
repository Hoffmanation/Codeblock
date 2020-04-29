package com.codeblock.service;

import com.codeblock.handler.BlogException;

public interface SecurityService {

	public String findLoggedInUsername();

	public boolean autologin(String username, String password) throws BlogException;
}
