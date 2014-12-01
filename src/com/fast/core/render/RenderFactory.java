package com.fast.core.render;

import com.fast.core.ViewType;

public class RenderFactory {

	private Render defaultRender;
	private static RenderFactory instance;

	public static RenderFactory getInstance() {
		if (instance == null) {
			instance = new RenderFactory();
		}
		return instance;
	}

	public Render getRender(String view, ViewType type) {
		if (ViewType.jsp == type) {
			defaultRender = new JSPRender(view);
		} else if (ViewType.json == type) {
			defaultRender = new JSONRender(view);
		} else if (ViewType.file == type) {
			defaultRender = new FileRender(view);
		}
		return defaultRender;
	}

	public Render getErrorRender() {
		return new ErrorRender();
	}
}
