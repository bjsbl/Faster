package com.fast.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fast.handler.ActionHandler;
import com.fast.log.Logger;
import com.fast.utils.StringUtils;

public class FastFilter implements Filter, ServletContextListener {

	private String encoding = "UTF-8";
	private int contextPathLength;
	private ActionHandler handler;
	protected static final Logger LOG = Logger.getLogger(FastFilter.class);

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		request.setCharacterEncoding(encoding);
		String target = request.getRequestURI();
		if (contextPathLength != 0) {
			target = target.substring(contextPathLength);
		}
		if (handler instanceof ActionHandler) {
			if (!handler.handle(target, request, response)) {
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String contextPath = filterConfig.getServletContext().getContextPath();
		contextPathLength = (contextPath == null || "/".equals(contextPath) ? 0 : contextPath.length());
		String scanPath = filterConfig.getInitParameter("componentScan");
		if (StringUtils.isBlank(scanPath)) {
			LOG.error("Param 'componentScan' Empty,Check Web.xml set");
			throw new ServletException();
		} else {
			scanPath = scanPath.replaceAll("\\.", "/");
			Context context = Context.getInstance();
			context.init(filterConfig.getServletContext(), scanPath);
			handler = context.getHandler();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

	}

}
