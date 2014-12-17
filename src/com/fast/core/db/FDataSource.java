package com.fast.core.db;

import javax.sql.DataSource;

public interface FDataSource {

	public void init(String driver, String url, String user, String password);

	public DataSource getDataSource();
}
