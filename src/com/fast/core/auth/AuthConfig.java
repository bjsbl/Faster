package com.fast.core.auth;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fast.handler.AuthHandler;
import com.fast.log.Logger;
import com.fast.utils.StringUtils;

public class AuthConfig {

	private static Logger log = Logger.getLogger(AuthConfig.class);

	private static String filterString;
	private static String nodeClass;
	private static Map<String, String> filterMap;

	public static void initXML() {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			InputStream in = AuthConfig.class.getClassLoader().getResourceAsStream("fast.xml");
			Document doc = builder.parse(in);
			Element root = doc.getDocumentElement();
			nodeClass = root.getAttribute("class");
			if (nodeClass == null) {
				throw new NullPointerException("bean's Class is Null");
			}
			// bean
			NodeList fields = root.getChildNodes();
			for (int i = 0; i < fields.getLength(); i++) {
				Node field = fields.item(i);
				// property
				if (field != null && field.getNodeType() == Node.ELEMENT_NODE) {
					NodeList sfields = field.getChildNodes();
					for (int n = 0; n < sfields.getLength(); n++) {
						Node sfield = sfields.item(n);
						// value
						if (sfield != null && sfield.getNodeType() == Node.ELEMENT_NODE) {
							filterString = sfield.getTextContent();
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public static void doIt(AuthHandler handler) throws Exception {
		if (filterString == null) {
			throw new NullPointerException("The FilterChain is Empty");
		}
		String[] lines = filterString.split("\n");
		filterMap = new HashMap<String, String>();
		for (String preLine : lines) {
			if (!StringUtils.isBlank(preLine)) {
				String[] nodeValues = preLine.trim().split("=");
				String key = nodeValues[0];
				String value = nodeValues[1];
				filterMap.put(key, value);
			}
		}
		Field field = handler.getClass().getDeclaredField("filterChain");
		field.setAccessible(true);
		field.set(handler, filterMap);
	}

	public static String toValues() {
		StringBuffer sbf = new StringBuffer();
		Set<String> keys = filterMap.keySet();
		for (String k : keys) {
			sbf.append(" [").append(k).append("=").append(filterMap.get(k)).append("] ");
		}
		return sbf.toString();
	}
}
