package com.fast.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fast.annotation.Transactional;
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
		for (Entry<String, Class> entry : views.getServiceEntrySet()) {
			Class<?> serviceClass = entry.getValue();
			Method[] methods = serviceClass.getMethods();
			for (Method curMethod : methods) {
				Transactional transactional = curMethod.getAnnotation(Transactional.class);
				if (transactional != null) {
					int level = transactional.value();

				}
			}
		}
	}

}
