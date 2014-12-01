package com.fast.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fast.controller.BaseController;

public class View {

	private final Map<String, Class<? extends BaseController>> map = new HashMap<String, Class<? extends BaseController>>();
	private final Map<String, String> viewPathMap = new HashMap<String, String>();

	public void addView(String controllerKey, Class<? extends BaseController> controllerClass, String viewPath) {
		controllerKey = controllerKey.trim();
		if ("".equals(controllerKey)) {
			throw new IllegalArgumentException("The controllerKey can not be blank");
		}
		if (controllerClass == null) {
			throw new IllegalArgumentException("The controllerClass can not be null");
		}
		if (!controllerKey.startsWith("/")) {
			controllerKey = "/" + controllerKey;
		}
		if (map.containsKey(controllerKey)) {
			throw new IllegalArgumentException("The controllerKey already exists: " + controllerKey);
		}
		map.put(controllerKey, controllerClass);
		if (viewPath == null || "".equals(viewPath.trim())) {
			viewPath = controllerKey;
		}
		viewPath = viewPath.trim();
		if (!viewPath.startsWith("/"))
			viewPath = "/" + viewPath;

		if (!viewPath.endsWith("/"))
			viewPath = viewPath + "/";

		viewPathMap.put(controllerKey, viewPath);
	}

	public Set<Entry<String, Class<? extends BaseController>>> getEntrySet() {
		return map.entrySet();
	}

	public String getViewPath(String key) {
		return viewPathMap.get(key);
	}
}
