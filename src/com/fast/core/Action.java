package com.fast.core;

import java.lang.reflect.Method;

import com.fast.controller.BaseController;

public class Action {

	private Class<? extends BaseController> controllerClass;
	private String controllerKey;
	private Method method;
	private String methodName;
	private String viewPath;

	public Action(String controllerKey, String actionKey, Class<? extends BaseController> controllerClass, Method method, String methodName, String viewPath) {
		this.controllerClass = controllerClass;
		this.controllerKey = controllerKey;
		this.method = method;
		this.methodName = methodName;
		this.viewPath = viewPath;
	}

	public Class<? extends BaseController> getControllerClass() {
		return controllerClass;
	}

	public String getControllerKey() {
		return controllerKey;
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
