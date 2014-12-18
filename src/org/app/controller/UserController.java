package org.app.controller;

import com.fast.annotation.Controller;
import com.fast.annotation.Path;
import com.fast.core.base.FastController;
import com.fast.log.Logger;

@Controller(path = { "/user" })
public class UserController extends FastController {

	private Logger log = Logger.getLogger(this.getClass());

	public void index() {
		log.info("index");
		renderJSP("index.jsp");
	}

	@Path(value = "/hello")
	public void hello() {
		log.info("user's hello");
		renderJSON("{name:'root'}");
	}
}
