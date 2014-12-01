package com.fast.core.render;

import java.io.IOException;

import javax.servlet.ServletException;

public class JSPRender extends Render {

	private static final long serialVersionUID = -1143848502997874309L;

	public JSPRender(String view) {
		this.view = view;
	}

	@Override
	public void render() {
		try {
			request.getRequestDispatcher(view).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
