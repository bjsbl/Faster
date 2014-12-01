package com.fast.core.db.table;

import java.util.HashMap;
import java.util.Map;

import com.fast.core.base.BaseModel;

public class TableMappings {

	private static TableMappings instance;

	private Map<Class<? extends BaseModel>, Table> tableMappings = new HashMap<Class<? extends BaseModel>, Table>();

	public static TableMappings getInstance() {
		if (instance == null) {
			instance = new TableMappings();
		}
		return instance;
	}

	public void addTableClass(Table table) throws Exception {
		if (table == null || table.getModelClass() == null) {
			throw new NullPointerException("The Object Class is null");
		}
		if (tableMappings.containsKey(table.getModelClass())) {
			throw new Exception("The Object class has existed");
		}
		tableMappings.put(table.getModelClass(), table);
	}

	public Table getTableMapping(Class model) {
		return tableMappings.get(model);
	}

}
