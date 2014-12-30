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
	protected static final String html401 = "<html><head><title>401 Unauthorized</title></head><body bgcolor='white'><center><h1>401 Unauthorized</h1></center><hr></body></html>";
	protected static final String debug = "<html><head><title>debug</title></head><body bgcolor='white'><center><h1>DEBUG</h1></center><hr></body></html>";

	@Override
	public void render() {
		response.setStatus(errorCode);
		PrintWriter writer = null;
		String code = "";
		try {
			response.setContentType("text/html");
			writer = response.getWriter();
			switch (errorCode) {
			case 404:
				code = html404;
				break;
			case 401:
				code = html401;
				break;
			default:
				code = debug;
			}
			writer.write(code);
			writer.flush();
		} catch (IOException e) {
			throw new RenderException(e);
		} finally {
			if (writer != null)
				writer.close();
		}
	}

}
