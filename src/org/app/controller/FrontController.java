package org.app.controller;

import org.app.service.BaseService;

import com.fast.annotation.Controller;
import com.fast.annotation.Path;
import com.fast.annotation.Resources;
import com.fast.core.base.FastController;
import com.fast.log.Logger;

@Controller(path = { "/" })
public class FrontController extends FastController {

	private Logger log = Logger.getLogger(this.getClass());

	@Resources
	private BaseService base;

	public void index() {
		log.info("/index");
		renderJSP("index.jsp");
	}

	public void pic() {
		renderCaptha(Math.random() + "N");
	}

	@Path(value = "/hello")
	public void path() {
		log.info("path");
		base.doSomeThings();
		renderJSON("{name:'root'}");
	}

	public void login() {
		log.info("Try to Login");
		renderJSP("success.jsp");
	}

	public void logout() {
		renderJSP("index.jsp");
	}
}
