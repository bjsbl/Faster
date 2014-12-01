package com.fast.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Handler {

	public abstract void handle(String target, HttpServletRequest request, HttpServletResponse response);
}
