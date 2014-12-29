package com.fast.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Handler {

	public boolean handle(String target, HttpServletRequest request, HttpServletResponse response);
}
