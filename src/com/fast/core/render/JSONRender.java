package com.fast.core.render;

import java.io.IOException;
import java.io.PrintWriter;

import com.fast.exception.RenderException;

public class JSONRender extends Render {

	private static final long serialVersionUID = 835703878666974524L;
	private String jsonText;

	public JSONRender(String jsonText) {
		this.jsonText = jsonText;
	}

	@Override
	public void render() {
		if (jsonText == null) {
			throw new RenderException("The JSON text can't null");
		}
		PrintWriter writer = null;
		try {
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/json;charset=UTF-8");
			writer = response.getWriter();
			writer.write(jsonText);
			writer.flush();
		} catch (IOException e) {
			throw new RenderException(e);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

}
