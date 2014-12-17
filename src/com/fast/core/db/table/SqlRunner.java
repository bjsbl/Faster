package com.fast.core.db.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class SqlRunner {

	public static int executeUpdate(Connection conn, String sql, Vector<Object> value) throws SQLException {
		PreparedStatement prs = conn.prepareStatement(sql);
		for (int n = 0; n < value.size(); n++) {
			prs.setObject(n + 1, value.get(n));
		}
		return prs.executeUpdate();
	}

	public static ResultSet executeQuery(Connection conn, String sql) throws SQLException {
		PreparedStatement prs = conn.prepareStatement(sql);
		return prs.executeQuery();
	}
}
