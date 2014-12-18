package com.fast.core.base;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fast.core.ViewType;
import com.fast.core.db.table.ModelBuilder;
import com.fast.core.render.Render;
import com.fast.core.render.RenderFactory;

public class FastController {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private Render render;

	public Render getRender() {
		return render;
	}

	public void init(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public void renderJSP(String view) {
		render = RenderFactory.getInstance().getRender(view, ViewType.jsp);
	}

	public void renderJSON(String view) {
		render = RenderFactory.getInstance().getRender(view, ViewType.json);
	}

	public void renderFile(String view) {
		render = RenderFactory.getInstance().getRender(view, ViewType.file);
	}

	public void renderCaptha() {
		render = RenderFactory.getInstance().getRender(null, ViewType.captha);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public FastController setAttr(String name, Object value) {
		request.setAttribute(name, value);
		return this;
	}

	public FastController removeAttr(String name) {
		request.removeAttribute(name);
		return this;
	}

	public String getPara(String name, String defaultValue) {
		String result = request.getParameter(name);
		return result != null && !"".equals(result) ? result : defaultValue;
	}

	private Integer toInt(String value, Integer defaultValue) {
		if (value == null || "".equals(value.trim()))
			return defaultValue;
		if (value.startsWith("N") || value.startsWith("n"))
			return -Integer.parseInt(value.substring(1));
		return Integer.parseInt(value);
	}

	private Boolean toBoolean(String value, Boolean defaultValue) {
		if (value == null || "".equals(value.trim()))
			return defaultValue;
		value = value.trim().toLowerCase();
		if ("1".equals(value) || "true".equals(value))
			return Boolean.TRUE;
		else if ("0".equals(value) || "false".equals(value))
			return Boolean.FALSE;
		throw new RuntimeException("Can not parse the parameter \"" + value + "\" to boolean value.");
	}

	public Boolean getParaToBoolean(String name, Boolean defaultValue) {
		return toBoolean(request.getParameter(name), defaultValue);
	}

	public FastController setCookie(Cookie cookie) {
		response.addCookie(cookie);
		return this;
	}

	public String getCookie(String name) {
		return getCookie(name, null);
	}

	public Cookie getCookieObject(String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies)
				if (cookie.getName().equals(name))
					return cookie;
		return null;
	}

	public String getCookie(String name, String defaultValue) {
		Cookie cookie = getCookieObject(name);
		return cookie != null ? cookie.getValue() : defaultValue;
	}

	public FastController setAttrs(Map<String, Object> attrMap) {
		for (Map.Entry<String, Object> entry : attrMap.entrySet())
			request.setAttribute(entry.getKey(), entry.getValue());
		return this;
	}

	public BaseModel getModelByRequest(Class<?> obj) {
		try {
			BaseModel base = ModelBuilder.generatorModel(obj, request);
			return base.getInstance(base);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
