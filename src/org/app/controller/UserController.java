package org.app.controller;

import org.app.model.User;

import com.fast.annotation.Controller;
import com.fast.core.base.FastController;

@Controller(path = { "/user" })
public class UserController extends FastController {

	public void reg() {
		User user = (User) getModelByRequest(User.class);
		renderJSP("index.jsp");
	}
}
