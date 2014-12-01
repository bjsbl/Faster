package com.fast.core.render;

import java.io.IOException;
import java.io.PrintWriter;

import com.fast.exception.RenderException;

public class ErrorRender extends Render {

	private static final long serialVersionUID = 1L;

	protected static final String html404 = "<html><head><title>404 Not Found</title></head><body bgcolor='white'><center><h1>404 Not Found</h1></center><hr></body></html>";

	@Override
	public void render() {
		response.setStatus(404);
		PrintWriter writer = null;
		try {
			response.setContentType("text/html");
			writer = response.getWriter();
			writer.write(html404);
			writer.flush();
		} catch (IOException e) {
			throw new RenderException(e);
		} finally {
			if (writer != null)
				writer.close();
		}
	}

}
