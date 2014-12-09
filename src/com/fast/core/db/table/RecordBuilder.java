package com.fast.core.db.table;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

<<<<<<< HEAD
import com.fast.log.Logger;

public class RecordBuilder {
	
	private static final Logger log = Logger.getLogger(RecordBuilder.class);
=======
public class RecordBuilder {
>>>>>>> 759fbe3f1537220d55188f6ae27c99e6cdcc5b5d

	public static List<Map<String, Object>> buildRecord(ResultSet rst) throws SQLException {
		ResultSetMetaData rsmd = rst.getMetaData();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
<<<<<<< HEAD
		int len = rsmd.getColumnCount();
		while (rst.next()) {
			Map<String, Object> colValue = new HashMap<String, Object>();
			for (int i = 1; i <= len; i++) {
=======
		Map<String, Object> colValue = new HashMap<String, Object>();
		int len = rsmd.getColumnCount();
		while (rst.next()) {
			for (int i = 0; i < len; i++) {
>>>>>>> 759fbe3f1537220d55188f6ae27c99e6cdcc5b5d
				colValue.put(rsmd.getColumnName(i).toLowerCase(), rst.getObject(rsmd.getColumnName(i)));
			}
			result.add(colValue);
		}
		return result;
	}

	public static Class<?> buildRecordToModel(ResultSet rst, Class<?> model) throws SQLException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		List<Map<String, Object>> value = buildRecord(rst);
		// return ModelBuilder.generatorRecord(value, model);
		return null;
	}
}
