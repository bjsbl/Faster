package com.fast.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fast.annotation.Before;
import com.fast.core.Action;
import com.fast.core.ActionMapping;
import com.fast.core.ViewType;
import com.fast.core.aop.Interceptor;
import com.fast.core.auth.AuthDefined;
import com.fast.core.auth.UserToken;
import com.fast.core.base.FastController;
import com.fast.core.render.Render;
import com.fast.core.render.RenderFactory;
import com.fast.log.Logger;

public class AuthHandler implements Handler {

	private static final Logger log = Logger.getLogger(AuthHandler.class);
	private AuthDefined authDetails;
	private ActionMapping actionMapping;
	private static final Object[] NULL_ARGS = new Object[0];
	private UserToken currentUser;

	public AuthHandler(ActionMapping actionMapping, AuthDefined authDetails) {
		this.actionMapping = actionMapping;
		log.info("Start AuthHandler instance");
		this.authDetails = authDetails;
	}

	@Override
	public boolean handle(String target, HttpServletRequest request, HttpServletResponse response) {
		if (target.indexOf(".") != -1) {
			return false;
		}
		if (authDetails.loginActionUrl.equals(target)) {
			Action action = actionMapping.getAction(target);
			if (action == null) {
				String qs = request.getQueryString();
				log.warn("404 Not Found: " + (qs == null ? target : target + "?" + qs));
				RenderFactory.getInstance().getErrorRender(404).setContext(request, response).render();
				return true;
			}
			try {
				currentUser = authDetails.createUserToken(request);
				currentUser = authDetails.validateUserTokenByToken(currentUser);
				if (currentUser == null) {
					String qs = request.getQueryString();
					log.warn("401 Unauthorized : " + (qs == null ? target : target + "?" + qs));
					RenderFactory.getInstance().getRender(authDetails.loginUrl, ViewType.jsp).setContext(request, response).render();
					return true;
				}
				FastController controller = (FastController) action.getControllerClass();
				controller.init(request, response);
				action.getMethod().invoke(controller, NULL_ARGS);
				Render render = controller.getRender();
				render.setContext(request, response).render();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else {
			Action action = actionMapping.getAction(target);
			if (action == null) {
				String qs = request.getQueryString();
				log.warn("404 Not Found: " + (qs == null ? target : target + "?" + qs));
				RenderFactory.getInstance().getErrorRender(404).setContext(request, response).render();
				return true;
			}
			boolean hasPermission = authDetails.validateRoleTokenByTarget(currentUser, target);
			if (!hasPermission) {
				request.setAttribute("error_code", "Request Failure,^_^ no Permission for " + target);
				RenderFactory.getInstance().getRender(authDetails.loginFailureUrl, ViewType.jsp).setContext(request, response).render();
				return true;
			}
			try {
				if (action.getMethod().isAnnotationPresent(Before.class)) {
					Before beforeIntercept = action.getMethod().getAnnotation(Before.class);
					Class<? extends Interceptor>[] beforeInterceptClass = beforeIntercept.value();
					for (Class<? extends Interceptor> interceptClass : beforeInterceptClass) {
						interceptClass.newInstance().intercept(action);
					}
				}
				FastController controller = (FastController) action.getControllerClass();
				controller.init(request, response);
				action.getMethod().invoke(controller, NULL_ARGS);
				Render render = controller.getRender();
				render.setContext(request, response).render();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
	}
}
