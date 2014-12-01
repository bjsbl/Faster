package com.fast.core.base;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.fast.core.db.table.RecordBuilder;
import com.fast.core.db.table.SqlBuilder;
import com.fast.core.db.table.SqlRunner;
import com.fast.core.db.table.Table;
import com.fast.core.db.table.TableMappings;
import com.fast.utils.StringUtils;

public class BaseModel implements Serializable {

	private Map<String, Object> fieldValue = new HashMap<String, Object>();

	public Map<String, Object> getFieldDetail() {
		return fieldValue;
	}

	public Table getTable() {
		Table rs = TableMappings.getInstance().getTableMapping(this.getClass());
		return rs;
	}

	public BaseModel set(String name, Object value) {
		fieldValue.put(name, value);
		return this;
	}

	public Object get(String name) {
		if (!StringUtils.isBlank(name)) {
			if (fieldValue.containsKey(name)) {
				return fieldValue.get(name);
			} else {
				throw new NullPointerException("The args Field is not exist");
			}
		} else {
			throw new NullPointerException("The args is null");
		}
	}

	public int save() {
		try {
			Vector<Object> obj = new Vector<Object>();
			String sql = SqlBuilder.insert(this, obj);
			System.out.println(sql);
			return SqlRunner.executeUpdate(sql, obj);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void deleteById() {

	}

	public void update() {

	}

	public List<Map<String, Object>> query() {
		try {
			String sql = SqlBuilder.queryByField(this, null);
			return RecordBuilder.buildRecord(SqlRunner.executeQuery(sql));
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
