package com.fast.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fast.controller.BaseController;
import com.fast.core.Action;
import com.fast.core.ActionMapping;
import com.fast.core.render.Render;
import com.fast.core.render.RenderFactory;
import com.fast.log.Logger;

public class ActionHandler extends Handler {

	private ActionMapping actionMapping;
	private static final Logger log = Logger.getLogger(ActionHandler.class);
	private static final Object[] NULL_ARGS = new Object[0];

	public ActionHandler(ActionMapping actionMapping) {
		this.actionMapping = actionMapping;
	}

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response) {
		if (target.indexOf(".") != -1) {
			return;
		}
		Action action = actionMapping.getAction(target);
		if (action == null) {
			String qs = request.getQueryString();
			log.warn("404 Not Found: " + (qs == null ? target : target + "?" + qs));
			RenderFactory.getInstance().getErrorRender().setContext(request, response).render();
			return;
		}
		try {
			BaseController controller = action.getControllerClass().newInstance();
			controller.init(request, response);
			action.getMethod().invoke(controller, NULL_ARGS);
			Render render = controller.getRender();
			render.setContext(request, response).render();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
