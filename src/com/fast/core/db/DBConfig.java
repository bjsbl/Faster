package com.fast.core.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DBConfig {

	private final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	private static DBConfig instance;
	private static DataSource dataSource;

	public static void addDataSource(FDataSource dataSource) {
		DBConfig.dataSource = dataSource.getDataSource();
	}

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
				DBConfig.dataSource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return threadLocal.get();
	}

}
