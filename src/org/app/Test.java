package org.app;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.app.controller.UserController;

import com.fast.annotation.Resources;

public class Test {

	/**
	 * @param args
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 */
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException {
		HashMap<String, Object> test = new HashMap<String, Object>();
		test.put("test", Class.forName("org.app.controller.UserController").newInstance());
		Field[] fields = UserController.class.getDeclaredFields();
		Object user = test.get("test");
		for (Field tmp : fields) {
			if (tmp.isAnnotationPresent(Resources.class)) {
				Class base = Class.forName(tmp.getType().getName());
				tmp.setAccessible(true);
				tmp.set(user, base.newInstance());
				System.out.println(tmp.getType().getName());
			}
		}
	}
}
