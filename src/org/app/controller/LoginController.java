package org.app.controller;

import org.app.aop.TestLog;
import org.app.model.User;
import org.app.service.UserService;

import com.fast.annotation.Before;
import com.fast.annotation.Controller;
import com.fast.annotation.Resources;
import com.fast.core.base.FastController;

@Controller(path = { "/com/app" })
public class LoginController extends FastController {

	@Resources
	private UserService userSerivce;

	private User user;

	@Before(TestLog.class)
	public void index() {
		System.out.println("=========Index============");
		setAttr("hello", "Hello World");
		setAttr("userList", userSerivce.query(user));
		renderJSP("/com/app/index.jsp");
	}

	public void main() {
		user = (User) getModelByRequest(User.class);
		index();
	}

	public void captha() {
		renderCaptha();
	}
}
