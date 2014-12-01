package com.fast.core;

import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.ServletContext;

import com.fast.annotation.Controller;
import com.fast.annotation.Service;
import com.fast.controller.BaseController;
import com.fast.handler.ActionHandler;
import com.fast.log.Logger;
import com.fast.utils.ClassSearcher;

public class Context {

	protected final Logger log = Logger.getLogger(getClass());

	private ActionMapping actionMapping;
	private ServiceMapping serviceMapping;
	private ActionHandler handler;
	private String contextPath = "";
	private String scanPath = "";

	public static Context getInstance() {
		return new Context();
	}

	public ActionHandler getHandler() {
		return handler;
	}

	public void init(ServletContext servletContext, String path) {
		this.contextPath = servletContext.getContextPath();
		this.scanPath = path;
		View views = initViews();
		initActionMapping(views);
		initServiceMapping(views);
		initHandler();
	}

	private void initActionMapping(View v) {
		actionMapping = new ActionMapping(v);
		actionMapping.buildActionMapping();
	}

	private void initServiceMapping(View v) {
		serviceMapping = new ServiceMapping(v);
		serviceMapping.buildServiceMapping();
	}

	public void initHandler() {
		ActionHandler actionHandler = new ActionHandler(actionMapping);
		handler = actionHandler;
	}

	private View initViews() {
		View views = new View();
		List<String> controllerList = ClassSearcher.getInstance(getClassPath()).run();
		for (String classFile : controllerList) {
			try {
				Class base = Class.forName(classFile);
				Annotation[] annotations = base.getAnnotations();
				for (Annotation ann : annotations) {
					if (ann instanceof Controller) {
						Class<? extends BaseController> controllerClass = (Class<? extends BaseController>) Class.forName(classFile);
						Controller controllerBind = (Controller) controllerClass.getAnnotation(Controller.class);
						if (controllerBind == null) {
							log.error(controllerClass.getName() + " extends baseController,but there no path;");
							continue;
						}
						String[] paths = controllerBind.path();
						for (String pathKey : paths) {
							pathKey = pathKey.trim();
							if (pathKey.equals("")) {
								log.error(controllerClass.getName() + "Path Empty is Null");
								continue;
							}
							views.addView(pathKey, controllerClass, pathKey);
						}
					} else if (ann instanceof Service) {
						Class baseSevice = Class.forName(classFile);
						views.addService(baseSevice.getName(), baseSevice);
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return views;
	}

	public String getClassPath() {
		String classPath = contextPath;
		try {
			classPath = Context.class.getResource("/").toURI().getPath();
		} catch (URISyntaxException e) {
			classPath = contextPath;
		}
		return classPath + scanPath;
	}

}
