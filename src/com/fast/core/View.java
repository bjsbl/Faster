package com.fast.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fast.controller.BaseController;

public class View {

	private Map<String, Object> map = new HashMap<String, Object>();
	private Map<String, String> viewPathMap = new HashMap<String, String>();
	private Map<String, Class> serviceMap = new HashMap<String, Class>();

	public void addView(String path, Class<? extends BaseController> controllerClass, String viewPath) {
		path = path.trim();
		if ("".equals(path)) {
			throw new IllegalArgumentException("The path can not be blank");
		}
		if (controllerClass == null) {
			throw new IllegalArgumentException("The Controller can not be null");
		}
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		if (map.containsKey(path)) {
			throw new IllegalArgumentException("The path already exists: " + path);
		}
		try {
			map.put(path, controllerClass.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if (viewPath == null || "".equals(viewPath.trim())) {
			viewPath = path;
		}
		viewPath = viewPath.trim();
		if (!viewPath.startsWith("/"))
			viewPath = "/" + viewPath;

		if (!viewPath.endsWith("/"))
			viewPath = viewPath + "/";

		viewPathMap.put(path, viewPath);
	}

	public void addService(String serviceKey, Class serviceImpl) {
		serviceKey = serviceKey.trim();
		if ("".equals(serviceKey)) {
			throw new IllegalArgumentException("The serviceKey can not be blank");
		}
		if (serviceImpl == null) {
			throw new IllegalArgumentException("The serviceImpl can not be null");
		}
		if (serviceMap.containsKey(serviceKey)) {
			throw new IllegalArgumentException("The serviceKey already exists: " + serviceKey);
		}
		serviceMap.put(serviceKey, serviceImpl);
	}

	public Class getServiceEntry(String packageName) {
		return serviceMap.get(packageName);
	}

	public Set<Entry<String, Object>> getEntrySet() {
		return map.entrySet();
	}

	public String getViewPath(String key) {
		return viewPathMap.get(key);
	}
}
