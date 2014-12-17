package com.fast.core.base;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

	public BaseModel getInstance(BaseModel param) {
		for (String key : fieldValue.keySet()) {
			try {
				String methodName = StringUtils.formatSetMethodName(key);
				Method[] method = param.getClass().getMethods();
				for (Method tmp : method) {
					if (tmp.getName().equals(methodName)) {
						tmp.invoke(param, fieldValue.get(key));
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return param;
	}
}
