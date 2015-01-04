package com.fast.core.db.tx;

import javax.sql.DataSource;

public class DefaultTransactionManager {

	private DataSource dataSource;

	public DefaultTransactionManager(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
