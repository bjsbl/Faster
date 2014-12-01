package com.fast.core.render;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fast.exception.RenderException;

public class FileRender extends Render {

	private static final long serialVersionUID = 1L;
	private File file;
	private String fileName;

	public FileRender(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void render() {
		if (fileName != null) {
			file = new File(fileName);
		}
		if (file == null || !file.isFile()) {
			RenderFactory.getInstance().getErrorRender().setContext(request, response).render();
			return;
		}
		response.addHeader("Content-disposition", "attachment; filename=" + file.getName());
		response.setContentType("application/octet-stream");
		response.setContentLength((int) file.length());
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(file));
			outputStream = response.getOutputStream();
			byte[] buffer = new byte[1024];
			for (int n = -1; (n = inputStream.read(buffer)) != -1;) {
				outputStream.write(buffer, 0, n);
			}
			outputStream.flush();
		} catch (Exception e) {
			throw new RenderException(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
