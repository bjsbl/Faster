package com.fast.core.db.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.fast.core.db.DBConfig;
import com.fast.log.Logger;

public class SqlRunner {

	private static Connection conn = DBConfig.getInstance().getThreadLocalConnection();

	public static int executeUpdate(String sql, Vector<Object> value) throws SQLException {
		PreparedStatement prs = conn.prepareStatement(sql);
		for (int n = 0; n < value.size(); n++) {
			prs.setObject(n + 1, value.get(n));
		}
		return prs.executeUpdate();
	}

	public static ResultSet executeQuery(String sql) throws SQLException {
		PreparedStatement prs = conn.prepareStatement(sql);
		return prs.executeQuery();
	}
}
