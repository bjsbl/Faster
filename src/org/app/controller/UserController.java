package org.app.controller;

import org.app.service.BaseService;
import org.app.service.UserService;

import com.fast.annotation.Controller;
import com.fast.annotation.Resources;
import com.fast.controller.BaseController;

@Controller(path = { "/user/app" })
public class UserController extends BaseController {

	@Resources
	private BaseService base;

	@Resources
	private UserService user;

	public void index() {
		base.doSomeThings();
		user.addRecord("admin");
		renderJSP("/com/app/index.jsp");
	}

	public void add() {
		user.addRecord("guest");
		String view = "c:\\export.xml";
		renderFile(view);
	}

	public void json() {
		user.addRecord("guest&admin");
		String view = "{a:text}";
		renderJSON(view);
	}
}
