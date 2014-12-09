package org.app.controller;

import org.app.aop.TestLog;
import org.app.model.User;

import com.fast.annotation.Before;
import com.fast.annotation.Controller;
import com.fast.core.base.BaseController;

@Controller(path = { "/com/app" })
public class LoginController extends BaseController {

	private User user;

	@Before(TestLog.class)
	public void index() {
		System.out.println("=========Index============");
		setAttr("hello", "Hello World");
		setAttr("userList", new User().query());
		renderJSP("/com/app/index.jsp");
	}

	public void main() {
<<<<<<< HEAD
		user = (User) getModelByRequest(User.class);
		user.save();
		index();
=======
		User user = (User) getModelByRequest(User.class);
		user.save();
		System.out.println("=========Main============");
>>>>>>> 759fbe3f1537220d55188f6ae27c99e6cdcc5b5d
	}
}
