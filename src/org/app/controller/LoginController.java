package org.app.controller;

import org.app.aop.TestLog;

import com.fast.annotation.Before;
import com.fast.annotation.Controller;
import com.fast.controller.BaseController;

@Controller(path = { "/com/app" })
public class LoginController extends BaseController {

	@Before(TestLog.class)
	public void index() {
		System.out.println("=========Index============");
		setAttr("hello", "Hello World");
		renderJSP("/com/app/index.jsp");
	}

	public void main() {
		System.out.println("==========Main===========");
	}
}
