package com.fast.core.auth;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.security.auth.message.config.AuthConfig;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fast.log.Logger;
import com.fast.utils.StringUtils;

public class ClassPathXmlAuthConfig implements com.fast.core.auth.AuthConfig {

	private static Logger log = Logger.getLogger(ClassPathXmlAuthConfig.class);

	private static final String signal = ">";
	private static ClassPathXmlAuthConfig instance;

	public static ClassPathXmlAuthConfig getInstance() {
		if (instance == null) {
			instance = new ClassPathXmlAuthConfig();
		}
		return instance;
	}

	private Map<String, Object> authClassMap = new HashMap<String, Object>();
	private Map<String, String> xmlMap = new HashMap<String, String>();
	private HashSet<RoleToken> roleToken = new HashSet<RoleToken>();
	private HashSet<UserToken> userToken = new HashSet<UserToken>();

	private void initXML() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		InputStream in = AuthConfig.class.getClassLoader().getResourceAsStream("fast.xml");
		Document doc = builder.parse(in);
		NodeList roots = doc.getElementsByTagName("bean");
		for (int i = 0; i < roots.getLength(); i++) {
			Node node = roots.item(i);
			if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
				readXML((Element) node);
			}
		}
	}

	private void readXML(Element root) {
		String nodeClass = root.getAttribute("class");
		if (nodeClass == null) {
			throw new NullPointerException("bean's Class is Null");
		}
		xmlMap.put("class", nodeClass);
		// bean
		NodeList fields = root.getChildNodes();
		for (int i = 0; i < fields.getLength(); i++) {
			Node field = fields.item(i);
			// property
			if (field != null && field.getNodeType() == Node.ELEMENT_NODE) {
				String propertyName = ((Element) field).getAttribute("name");
				if (propertyName.indexOf("Url") == -1) {
					NodeList sfields = field.getChildNodes();
					for (int n = 0; n < sfields.getLength(); n++) {
						Node sfield = sfields.item(n);
						// value
						if (sfield != null && sfield.getNodeType() == Node.ELEMENT_NODE) {
							xmlMap.put(nodeClass + signal + propertyName, sfield.getTextContent());
						}
					}
				} else {
					xmlMap.put(nodeClass + signal + propertyName, field.getTextContent());
					authClassMap.put(nodeClass + signal + propertyName, field.getTextContent());
				}
			}
		}
	}

	@Override
	public boolean build() {
		try {
			initXML();
			String filter = xmlMap.get("com.fast.core.auth.AuthDefined>filterChain");
			String users = xmlMap.get("com.fast.core.auth.AuthDefined>userDetails");
			mapAuthorityBean(formatString2Map(filter));
			mapUserBean(formatString2Map(users));
			authClassMap.put("com.fast.core.auth.AuthDefined>userTokenDetails", userToken);
			for (UserToken t : userToken) {
				Set<RoleToken> roles = t.roles;
				String r = "";
				for (RoleToken t1 : roles) {
					r += t1.tokenName + ",";
					Set<String> u = t1.urls;
					String urls = "";
					for (String url : u) {
						urls += url + ",";
					}
					r += "[" + urls + "]";
				}
				log.info("user: " + t.userName + " ;Roles: " + r);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	private HashMap<String, String> formatString2Map(String arg) {
		String[] lines = arg.split("\n");
		HashMap<String, String> filterMap = new HashMap<String, String>();
		for (String preLine : lines) {
			if (!StringUtils.isBlank(preLine)) {
				String[] nodeValues = StringUtils.trimAll(preLine).split("=");
				String key = nodeValues[0];
				String value = nodeValues[1];
				if (filterMap.containsKey(key)) {
					String tmpValue = filterMap.get(key);
					filterMap.put(key, tmpValue + signal + value);
				} else {
					filterMap.put(key, value);
				}
			}
		}
		return filterMap;
	}

	// admin = /admin
	// guest = /front/hello>/front/user/*
	private void mapAuthorityBean(HashMap<String, String> arg) {
		RoleToken role = null;
		Set<String> roleSet = arg.keySet();
		for (String roleName : roleSet) {
			role = new RoleToken();
			role.tokenName = roleName;
			String url = arg.get(roleName);
			if (url.indexOf(signal) > 0) {
				String[] urls = url.split(signal);
				role.urls.addAll(Arrays.asList(urls));
			} else {
				role.urls.add(url);
			}
			roleToken.add(role);
		}
	}

	// admin=admin1,admin2
	// guest=guest1,guest2>user1
	private void mapUserBean(HashMap<String, String> arg) throws Exception {
		Set<String> roleSet = arg.keySet();
		for (String roleName : roleSet) {
			if (isRegistryRole(roleName)) {
				String users = arg.get(roleName);
				if (users.indexOf(signal) > 0) {
					String[] userArray = users.split(signal);
					for (String array : userArray) {
						splitUsers(array, roleName);
					}
				} else {
					splitUsers(users, roleName);
				}
			} else {
				throw new Exception(roleName + " undefined in Roles");
			}
		}
	}

	private void splitUsers(String array, String role) {
		String[] subArray = array.split(",");
		UserToken user = null;
		for (String sub : subArray) {
			if (isRegistryUser(sub)) {
				user = getUserTokenByName(sub);
				if (!hasExistsRoles(user, role)) {
					user.roles.add(getRoleTokenByName(role));
				}
			} else {
				user = new UserToken();
				user.userName = sub;
				user.roles.add(getRoleTokenByName(role));
				userToken.add(user);
			}
		}
	}

	private boolean hasExistsRoles(UserToken user, String roleName) {
		Set<RoleToken> roles = user.roles;
		for (RoleToken r : roles) {
			if (r.tokenName.equalsIgnoreCase(roleName)) {
				return true;
			}
		}
		return false;
	}

	private boolean isRegistryRole(String name) {
		if (StringUtils.isBlank(name)) {
			return false;
		}
		for (RoleToken tmp : roleToken) {
			if (tmp.tokenName.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	private boolean isRegistryUser(String name) {
		if (StringUtils.isBlank(name)) {
			return false;
		}
		for (UserToken tmp : userToken) {
			if (tmp.userName.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	private RoleToken getRoleTokenByName(String name) {
		RoleToken rs = null;
		for (RoleToken tmp : roleToken) {
			if (tmp.tokenName.equalsIgnoreCase(name)) {
				rs = tmp;
				break;
			}
		}
		return rs;
	}

	private UserToken getUserTokenByName(String name) {
		UserToken rs = null;
		for (UserToken tmp : userToken) {
			if (tmp.userName.equalsIgnoreCase(name)) {
				rs = tmp;
				break;
			}
		}
		return rs;
	}

	public AuthDefined instanceAuthDefined() {
		try {
			String objClass = xmlMap.get("class");
			AuthDefined clz = (AuthDefined) Class.forName(objClass).newInstance();
			Field[] fields = clz.getClass().getDeclaredFields();
			for (Field temp : fields) {
				temp.setAccessible(true);
				temp.set(clz, authClassMap.get(objClass + signal + temp.getName()));
			}
			return clz;
		} catch (Exception e) {
			log.equals(e.getMessage());
			return null;
		}
	}

	@Override
	public String toString() {
		StringBuffer sbf = new StringBuffer();
		Set<String> keys = xmlMap.keySet();
		for (String k : keys) {
			sbf.append(" [").append(StringUtils.trimAll(k)).append("=").append(StringUtils.trimAll(xmlMap.get(k))).append("] ");
		}
		return sbf.toString();
	}

}
