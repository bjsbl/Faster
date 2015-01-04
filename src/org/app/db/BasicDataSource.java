package org.app.db;

import javax.sql.DataSource;

import com.fast.annotation.DataScoure;
import com.fast.core.db.FDataSource;

@DataScoure
public class BasicDataSource implements FDataSource {

	private org.apache.commons.dbcp.BasicDataSource dataSource;
	private String driver = "com.mysql.jdbc.Driver";;
	private String url = "jdbc:mysql://localhost:3306/sants?characterEncoding=utf8";
	private String user = "sants";
	private String pwd = "123456";

	@Override
	public DataSource getDataSource() {
		if (dataSource == null) {
			init(driver, url, user, pwd);
		}
		return dataSource;
	}

	@Override
	public void init(String driver, String url, String user, String password) {
		dataSource = new org.apache.commons.dbcp.BasicDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		dataSource.setDriverClassName(driver);
	}
}
