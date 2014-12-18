package com.fast.core.base;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.fast.core.db.DBConfig;
import com.fast.core.db.table.RecordBuilder;
import com.fast.core.db.table.SqlBuilder;
import com.fast.core.db.table.SqlRunner;
import com.fast.log.Logger;

public class FastService {

	private Logger log = Logger.getLogger(FastService.class);

	private DBConfig dbConfig = DBConfig.getInstance();

	protected int save(BaseModel obj) {
		try {
			Connection conn = dbConfig.getThreadLocalConnection();
			Vector<Object> fields = new Vector<Object>();
			String sql = SqlBuilder.insert(obj, fields);
			log.debug(sql);
			return SqlRunner.executeUpdate(conn, sql, fields);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	protected List<Map<String, Object>> query(BaseModel obj) {
		try {
			Connection conn = dbConfig.getThreadLocalConnection();
			String sql = SqlBuilder.queryByFieldString(obj, null);
			log.debug(sql);
			return RecordBuilder.buildRecord(SqlRunner.executeQuery(conn, sql));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
