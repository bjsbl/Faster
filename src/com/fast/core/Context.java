package com.fast.core;

import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.ServletContext;

import com.fast.annotation.Controller;
import com.fast.controller.BaseController;
import com.fast.handler.ActionHandler;
import com.fast.handler.Handler;
import com.fast.log.Logger;
import com.fast.utils.ClassSearcher;

public class Context {

	protected final Logger log = Logger.getLogger(getClass());

	private ActionMapping actionMapping;
	private Handler handler;
	private ServletContext servletContext;
	private String contextPath = "";
	private String scanPath = "";

	public static Context getInstance() {
		return new Context();
	}

	public Handler getHandler() {
		return handler;
	}

	public void initHandler() {
		Handler actionHandler = new ActionHandler(actionMapping);
		handler = actionHandler;
	}
	
	public void initRender(){
		
	}

	public void init(ServletContext servletContext, String path) {
		this.servletContext = servletContext;
		this.contextPath = servletContext.getContextPath();
		this.scanPath = path;
		initActionMapping();
		initHandler();
	}

	private void initActionMapping() {
		actionMapping = new ActionMapping(initController());
		actionMapping.buildActionMapping();
	}

	private View initController() {
		View views = new View();
		StringBuffer actionLogs = new StringBuffer();
		List<String> controllerList = ClassSearcher.getInstance(getClassPath()).run();
		for (String classFile : controllerList) {
			try {
				Class<? extends BaseController> controllerClass = (Class<? extends BaseController>) Class.forName(classFile);
				Controller controllerBind = (Controller) controllerClass.getAnnotation(Controller.class);
				if (controllerBind == null) {
					log.error(controllerClass.getName() + " extends baseController,but there no path;");
					continue;
				}
				String[] controllerKeys = controllerBind.controllerKey();
				for (String controllerKey : controllerKeys) {
					controllerKey = controllerKey.trim();
					if (controllerKey.equals("")) {
						log.error(controllerClass.getName() + "Path Empty is Null");
						continue;
					}
					actionLogs.append(controllerClass.getName()).append("  Path:").append(controllerKey).append("\r\n");
					views.addView(controllerKey, controllerClass, controllerKey);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		log.info(actionLogs.toString());
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
