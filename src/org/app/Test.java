package org.app;

import org.app.model.User;

import com.fast.core.db.table.SqlBuilder;

public class Test {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		User user = new User();
		user.setId("1");
		user.setName("admin");
	}
}
