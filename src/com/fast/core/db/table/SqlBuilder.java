package com.fast.core.db.table;

import java.util.Map;
import java.util.Vector;

import com.fast.core.base.BaseModel;
import com.fast.utils.StringUtils;

public class SqlBuilder {

	public static String insert(BaseModel model, Vector<Object> value) throws InstantiationException, IllegalAccessException {
		StringBuffer sql = new StringBuffer();
		Table table = model.getTable();
		Map<String, Object> columns = model.getFieldDetail();
		StringBuffer columNames = new StringBuffer();
		StringBuffer replaceChar = new StringBuffer();
		for (String col : columns.keySet()) {
			columNames.append(col).append(",");
			replaceChar.append("?").append(",");
			value.add(columns.get(col));
		}
		sql.append("insert into ").append(table.getName()).append(" ( ");
		sql.append(StringUtils.substringLastSuffix(columNames.toString())).append(" )").append(" values (");
		sql.append(StringUtils.substringLastSuffix(replaceChar.toString())).append(")");
		return sql.toString();
	}

	public static String queryByField(BaseModel model, String where) throws InstantiationException, IllegalAccessException {
		StringBuffer sql = new StringBuffer();
		Table table = model.getTable();
		Map<String, Object> columns = model.getFieldDetail();
		StringBuffer columNames = new StringBuffer();
		sql.append("select ");
		for (String col : columns.keySet()) {
			columNames.append(col).append(",");
		}
		sql.append(StringUtils.substringLastSuffix(columNames.toString())).append(" from ").append(table.getName());
		if (!StringUtils.isBlank(where)) {
			sql.append(where);
		}
		return sql.toString();
	}

	public static String deleteByField(BaseModel model, String where) {
		StringBuffer sql = new StringBuffer();
		Table table = model.getTable();
		sql.append("delete ").append(" from ").append(table.getName());
		if (!StringUtils.isBlank(where)) {
			sql.append(where);
		}
		return sql.toString();
	}

	public static String updateModel(BaseModel model, String primaryKey, Object keyValue) throws InstantiationException, IllegalAccessException {
		StringBuffer sql = new StringBuffer();
		StringBuffer subColumn = new StringBuffer();
		Table table = model.getTable();
		Map<String, Object> columns = model.getFieldDetail();
		for (String col : columns.keySet()) {
			if (!StringUtils.isEmpty(columns.get(col))) {
				subColumn.append(col).append("=").append(columns.get(col)).append(",");
			}
		}
		sql.append("update ").append(table.getName()).append(" set ").append(subColumn.toString()).append(" where ").append(primaryKey).append("=").append(keyValue);
		return sql.toString();
	}
}
