package com.fast.core.db.table;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fast.log.Logger;

public class RecordBuilder {

	public static List<Map<String, Object>> buildRecord(ResultSet rst) throws Exception {
		ResultSetMetaData rsmd = rst.getMetaData();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int len = rsmd.getColumnCount();
		while (rst.next()) {
			Map<String, Object> colValue = new HashMap<String, Object>();
			for (int i = 1; i <= len; i++) {
				colValue.put(rsmd.getColumnName(i).toLowerCase(), rst.getObject(rsmd.getColumnName(i)));
			}
			result.add(colValue);
		}
		return result;
	}

	public static List<Class<?>> buildRecordToModel(ResultSet rst, Class<?> model) throws Exception {
		List<Map<String, Object>> value = buildRecord(rst);
		List<Class<?>> rs = new LinkedList<Class<?>>();
		for (Map<String, Object> tmp : value) {
			rs.add((Class<?>) ModelBuilder.generatorRecord(tmp, model));
		}
		return rs;
	}
}
