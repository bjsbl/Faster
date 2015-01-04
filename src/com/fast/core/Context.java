package com.fast.core;

import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.ServletContext;

import com.fast.annotation.Controller;
import com.fast.annotation.DataScoure;
import com.fast.annotation.Model;
import com.fast.annotation.Service;
import com.fast.core.auth.AuthDefined;
import com.fast.core.auth.ClassPathXmlAuthConfig;
import com.fast.core.base.BaseModel;
import com.fast.core.db.DBConfig;
import com.fast.core.db.FDataSource;
import com.fast.core.db.table.Table;
import com.fast.core.db.table.TableMappings;
import com.fast.handler.ActionHandler;
import com.fast.handler.AuthHandler;
import com.fast.handler.Handler;
import com.fast.log.Logger;
import com.fast.utils.ClassSearcher;
import com.fast.utils.StringUtils;

public class Context {

	protected final Logger log = Logger.getLogger(getClass());

	private ActionMapping actionMapping;
	private ServiceMapping serviceMapping;
	private TableMappings tableMappings;
	private Handler handlers;
	private FDataSource dataSource;
	private String contextPath = "";
	private String scanPath = "";

	public static Context getInstance() {
		return new Context();
	}

	public Handler getHandlers() {
		return handlers;
	}

	public void init(ServletContext servletContext, String path) {
		this.contextPath = servletContext.getContextPath();
		this.scanPath = path;
		initTableMappings();
		View views = scanClassContainer();
		initActionMapping(views);
		initServiceMapping(views);
		initHandler();
		initDataSource();
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
		ClassPathXmlAuthConfig config = ClassPathXmlAuthConfig.getInstance();
		if (config.build()) {
			AuthDefined authDetails = config.instanceAuthDefined();
			handlers = new AuthHandler(actionMapping, authDetails);
		} else {
			handlers = new ActionHandler(actionMapping);
		}
	}

	private void initTableMappings() {
		tableMappings = TableMappings.getInstance();
	}

	private void initDataSource() {
		DBConfig.addDataSource(dataSource);
	}

	private View scanClassContainer() {
		View views = new View();
		List<String> controllerList = ClassSearcher.getInstance(getClassPath()).run();
		for (String classFile : controllerList) {
			try {
				Class<?> base = Class.forName(classFile);
				Annotation[] annotations = base.getAnnotations();
				for (Annotation ann : annotations) {
					if (ann instanceof Controller) {
						Controller controllerBind = (Controller) base.getAnnotation(Controller.class);
						if (controllerBind == null) {
							log.error(base.getName() + " Path is Null");
							continue;
						}
						String[] paths = controllerBind.path();
						for (String pathKey : paths) {
							pathKey = pathKey.trim();
							if (pathKey.equals("")) {
								log.error(base.getName() + "Path is Null");
								continue;
							}
							views.addView(pathKey, base, pathKey);
						}
					} else if (ann instanceof Service) {
						views.addService(base.getName(), base);
					} else if (ann instanceof Model) {
						String modelName = StringUtils.defaultIfEmpty(((Model) ann).name(), base.getName());
						log.info("Found " + modelName);
						tableMappings.addTableClass(Table.getInstance().setName(modelName).setModelClass((Class<? extends BaseModel>) base));
					} else if (ann instanceof DataScoure) {
						dataSource = (FDataSource) base.newInstance();
					}
				}
			} catch (Exception e) {
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
