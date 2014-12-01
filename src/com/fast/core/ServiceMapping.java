package com.fast.core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fast.annotation.Resources;
import com.fast.log.Logger;

public class ServiceMapping {

	protected static final Logger LOG = Logger.getLogger(ServiceMapping.class);

	private View views;
	private Map<String, Class> mapping = new HashMap<String, Class>();

	public ServiceMapping(View views) {
		this.views = views;
	}

	public void buildServiceMapping() {
		mapping.clear();
		StringBuffer actionLogs = new StringBuffer();
		actionLogs.append("\r");
		for (Entry<String, Object> entry : views.getEntrySet()) {
			Object controllerClass = entry.getValue();
			Field[] fields = controllerClass.getClass().getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(Resources.class)) {
					try {
						field.setAccessible(true);
						String t = field.getType().getName();
						Class inject = views.getServiceEntry(t);
						field.set(controllerClass, inject.newInstance());
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					}
					actionLogs.append(controllerClass.getClass().getPackage().getName() + " >" + field.getName() + " has inject!\r");
				}
			}
		}
		actionLogs.append("\r");
		LOG.info(actionLogs.toString());
	}

}
