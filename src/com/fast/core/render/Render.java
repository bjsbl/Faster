package com.fast.core.render;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Render implements Serializable {

	private static final long serialVersionUID = 5417658393233728010L;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected String view;

	public Render setContext(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		return this;
	}

	public abstract void render();

}
