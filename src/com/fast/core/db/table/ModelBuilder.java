package com.fast.core.db.table;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.fast.core.base.BaseModel;
import com.fast.log.Logger;
import com.fast.utils.StringUtils;

public class ModelBuilder {

	protected static final Logger log = Logger.getLogger(ModelBuilder.class);

	public static BaseModel generatorModel(Class<?> obj, HttpServletRequest request) throws Exception {
		Map<String, String[]> requestMap = request.getParameterMap();
		BaseModel real = (BaseModel) obj.newInstance();
		for (Entry<String, String[]> e : requestMap.entrySet()) {
			String paraKey = e.getKey();
			Object value = e.getValue()[0] != null ? e.getValue()[0] : "";
			real.set(paraKey, value);
		}
		log.info(real.getFieldDetail().toString());
		return real;
	}

	public static Object generatorRecord(Map<String, Object> value, Class<?> obj) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		Field[] srcFields = obj.getDeclaredFields();
		Object objClass = obj.newInstance();
		Set<String> fields = value.keySet();
		for (Field tmp : srcFields) {
			if (fields.contains(tmp.getName().toLowerCase())) {
				tmp.setAccessible(true);
				tmp.set(objClass, value.get(tmp.getName().toLowerCase()));
			}
		}
		return objClass;
	}
}
