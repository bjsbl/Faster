package com.fast.core.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConfig {

	private final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	private static DBConfig instance;

	public static DBConfig getInstance() {
		if (instance == null) {
			instance = new DBConfig();
		}
		return instance;
	}

	public void setThreadLocalConnection(Connection connection) {
		threadLocal.set(connection);
	}

	public Connection getThreadLocalConnection() {
		Connection conn = threadLocal.get();
		if (conn == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cake?useUnicode=true&characterEncoding=UTF-8", "jxt", "jxt");
				threadLocal.set(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return threadLocal.get();
	}

}
