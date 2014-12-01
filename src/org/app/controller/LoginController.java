package org.app.controller;

import com.fast.annotation.Controller;
import com.fast.controller.BaseController;

@Controller(controllerKey = { "/com/app" })
public class LoginController extends BaseController {

	public void index() {
		System.out.println("=========Index============");
		setAttr("hello", "Hello World");
		renderJSP("/com/app/index.jsp");
	}

	public void main() {
		System.out.println("==========Main===========");
	}
}
