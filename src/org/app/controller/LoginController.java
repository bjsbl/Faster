package org.app.controller;

import org.app.aop.TestLog;
import org.app.model.User;

import com.fast.annotation.Before;
import com.fast.annotation.Controller;
import com.fast.core.base.BaseController;

@Controller(path = { "/com/app" })
public class LoginController extends BaseController {

	@Before(TestLog.class)
	public void index() {
		System.out.println("=========Index============");
		setAttr("hello", "Hello World");
		renderJSP("/com/app/index.jsp");
	}

	public void main() {
		User user = (User) getModelByRequest(User.class);
		user.save();
		System.out.println("=========Main============");
	}
}
