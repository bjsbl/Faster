package com.fast.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fast.controller.BaseController;
import com.fast.log.Logger;

public class ActionMapping {

	protected static final Logger LOG = Logger.getLogger(ActionMapping.class);

	private View views;
	private static final String DEFAULT = "/";
	private Map<String, Action> mapping = new HashMap<String, Action>();

	public ActionMapping(View views) {
		this.views = views;
	}

	private Set<String> buildExcludedMethodName() {
		Set<String> excludedMethodName = new HashSet<String>();
		Method[] methods = BaseController.class.getMethods();
		for (Method m : methods) {
			if (m.getParameterTypes().length == 0)
				excludedMethodName.add(m.getName());
		}
		return excludedMethodName;
	}

	public void buildActionMapping() {
		mapping.clear();
		StringBuffer actionLogs = new StringBuffer();
		Set<String> excludedMethodName = buildExcludedMethodName();
		for (Entry<String, Class<? extends BaseController>> entry : views.getEntrySet()) {
			Class<? extends BaseController> controllerClass = entry.getValue();
			Method[] methods = controllerClass.getMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				if (!excludedMethodName.contains(methodName) && method.getParameterTypes().length == 0) {
					String controllerKey = entry.getKey();
					String actionKey = controllerKey.equals(DEFAULT) ? DEFAULT + methodName : controllerKey + DEFAULT + methodName;
					if (mapping.containsKey(actionKey)) {
						LOG.error(actionKey + " has used;");
						continue;
					}
					Action action = new Action(controllerKey, actionKey, controllerClass, method, methodName, views.getViewPath(controllerKey));
					mapping.put(actionKey, action);
					actionLogs.append(actionKey).append("==>>").append(controllerClass).append(" Method:").append(methodName).append("\r");
				}
			}
		}
		LOG.info(actionLogs.toString());
	}

	public Action getAction(String url) {
		Action action = mapping.get(url);
		if (action != null) {
			return action;
		}
		int i = url.lastIndexOf(DEFAULT);
		if (i != -1) {
			action = mapping.get(url.substring(0, i));
		}
		return action;
	}
}
