package com.fast.core;

import java.lang.reflect.Method;

public class Action {

	private Object controllerClass;
	private String controllerPath;
	private Method method;
	private String methodName;
	private String viewPath;

	public Action(String controllerPath, String actionKey, Object controllerClass, Method method, String methodName, String viewPath) {
		this.controllerClass = controllerClass;
		this.controllerPath = controllerPath;
		this.method = method;
		this.methodName = methodName;
		this.viewPath = viewPath;
	}

	public Object getControllerClass() {
		return controllerClass;
	}

	public String getControllerPath() {
		return controllerPath;
	}

	public Method getMethod() {
		return method;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getViewPath() {
		return viewPath;
	}

}
