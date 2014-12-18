package org.app.controller;

import com.fast.annotation.Controller;
import com.fast.annotation.Path;
import com.fast.core.base.FastController;
import com.fast.log.Logger;

@Controller(path = { "/" })
public class FrontController extends FastController {

	private Logger log = Logger.getLogger(this.getClass());

	public void index() {
		log.info("/index");
		renderJSP("index.jsp");
	}

	@Path(value = "/hello")
	public void path() {
		log.info("path");
		renderJSON("{name:'root'}");
	}
}
