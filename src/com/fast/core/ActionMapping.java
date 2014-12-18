package com.fast.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fast.annotation.Path;
import com.fast.core.base.FastController;
import com.fast.log.Logger;
import com.fast.utils.StringUtils;

public class ActionMapping {

	protected static final Logger LOG = Logger.getLogger(ActionMapping.class);

	private View views;
	private static final String DEFAULT = "/";
	private Map<String, Action> mapping = new HashMap<String, Action>();

	public ActionMapping(View views) {
		this.views = views;
	}

	public void buildActionMapping() {
		mapping.clear();
		for (Entry<String, Object> entry : views.getEntrySet()) {
			Object controllerClass = entry.getValue();
			String controllerKey = entry.getKey();
			Method[] methods = controllerClass.getClass().getMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				if (method.getDeclaringClass() == controllerClass.getClass() && method.getParameterTypes().length == 0) {
					String actionKey = controllerKey.equals(DEFAULT) ? DEFAULT + methodName : controllerKey + DEFAULT + methodName;
					Path sub = method.getAnnotation(Path.class);
					if (sub != null) {
						String value = sub.value();
						if (!StringUtils.isEmpty(value)) {
							value = value.startsWith(DEFAULT) ? value : DEFAULT + value;
							actionKey = controllerKey.endsWith(DEFAULT) ? controllerKey.substring(0, controllerKey.length() - 1) + value : controllerKey + value;
						}
					}
					if (mapping.containsKey(actionKey)) {
						LOG.error(actionKey + " has used;");
						continue;
					}
					Action action = new Action(controllerKey, actionKey, controllerClass, method, methodName, views.getViewPath(controllerKey));
					mapping.put(actionKey, action);
					if (ApplicationConstants.DEV_MODE) {
						LOG.info(controllerClass + " > " + actionKey);
					}
				}
			}
		}
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
