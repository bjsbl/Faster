package com.fast.core.render;

import java.io.IOException;
import java.io.PrintWriter;

import com.fast.exception.RenderException;

public class ErrorRender extends Render {

	private static final long serialVersionUID = 1L;

	private int errorCode;

	public ErrorRender(int errorCode) {
		this.errorCode = errorCode;
	}

	protected static final String html404 = "<html><head><title>404 Not Found</title></head><body bgcolor='white'><center><h1>404 Not Found</h1></center><hr></body></html>";

	@Override
	public void render() {
		response.setStatus(errorCode);
		PrintWriter writer = null;
		try {
			response.setContentType("text/html");
			writer = response.getWriter();
			switch (errorCode) {
			case 404:
				writer.write(html404);
				break;
			}
			writer.flush();
		} catch (IOException e) {
			throw new RenderException(e);
		} finally {
			if (writer != null)
				writer.close();
		}
	}

}
