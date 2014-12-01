package com.fast.core.render;

public class RenderFactory {

	private Render defaultRender;
	private static RenderFactory instance;

	public static RenderFactory getInstance() {
		if (instance == null) {
			instance = new RenderFactory();
		}
		return instance;
	}

	public Render getRender(String view) {
		defaultRender = new JSPRender(view);
		return defaultRender;
	}

	public Render getErrorRender() {
		return new ErrorRender();
	}
}
