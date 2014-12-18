package com.fast.handler;

import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fast.core.AuthType;
import com.fast.log.Logger;

public class AuthHandler implements Handler {

	private static final Logger log = Logger.getLogger(AuthHandler.class);
	private Vector<String> urls = new Vector<String>();
	private AuthType type;
	private Map<String, String> filterChain;

	public AuthHandler() {
		log.debug("Start AuthHandler instance");
		type = AuthType.url;
	}

	protected void addUrls(String url) {
		if (!urls.contains(url)) {
			urls.add(url.toLowerCase().trim());
		}
	}

	@Override
	public boolean handle(String target, HttpServletRequest request, HttpServletResponse response) {
		if (type == AuthType.url) {
			for (String url : urls) {
				if (target.toLowerCase().trim().startsWith(url)) {
					return false;
				}
			}
		} else if (type == AuthType.regexp) {
			for (String url : urls) {
				if (Pattern.matches(url, target)) {
					return false;
				}
			}
		}
		return true;
	}
}
