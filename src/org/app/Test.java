package org.app;

import com.fast.core.auth.AuthConfig;
import com.fast.handler.AuthHandler;

public class Test {

	public static void main(String[] args) throws Exception {
		// Map<String, Object> values = new HashMap<String, Object>();
		// values.put("username", "admin");
		// values.put("password", "123456");
		// User user = (User) ModelBuilder.generatorRecord(values, User.class);
		// System.out.println(user.userName);
		// System.out.println(user.password);
		AuthHandler handler = new AuthHandler();
		AuthConfig.initXML();
		AuthConfig.doIt(handler);
		System.out.println(AuthConfig.toValues());
	}
}
