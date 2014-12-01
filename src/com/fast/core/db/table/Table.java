package com.fast.core.db.table;

import java.util.Map;

import com.fast.core.base.BaseModel;

public class Table {

	private String name;
	private String primaryKey;
	private Map<String, Class<?>> columnTypeMap;
	private Class<? extends BaseModel> modelClass;
	private static Table instance;

	public static Table getInstance() {
		if (instance == null) {
			instance = new Table();
		}
		return instance;
	}

	public void setColumnType(String columnName, Class columnType) {
		columnTypeMap.put(columnName, columnType);
	}

	public Class getColumnType(String columnName) {
		return columnTypeMap.get(columnName);
	}

	public Class<? extends BaseModel> getModelClass() {
		return modelClass;
	}

	public Table setModelClass(Class<? extends BaseModel> modelClass) {
		this.modelClass = modelClass;
		return instance;
	}

	public String getName() {
		return name;
	}

	public Table setName(String name) {
		this.name = name;
		return this;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Map<String, Class<?>> getColumnTypeMap() {
		return columnTypeMap;
	}

}
