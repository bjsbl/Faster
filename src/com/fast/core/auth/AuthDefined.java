package com.fast.core.auth;

import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fast.utils.StringUtils;

public class AuthDefined {

	public String loginUrl;
	public String loginActionUrl;
	public String loginSuccessUrl;
	public String loginFailureUrl;

	public Set<UserToken> userTokenDetails;

	public UserToken validateUserTokenByToken(UserToken token) {
		if (StringUtils.isEmpty(token)) {
			return null;
		} else {
			for (UserToken tmp : userTokenDetails) {
				if (tmp.userName.equals(token.userName)) {
					return tmp;
				}
			}
		}
		return null;
	}

	public boolean validateRoleTokenByTarget(UserToken user, String target) {
		if (StringUtils.isEmpty(user)) {
			return false;
		}
		Set<RoleToken> roles = user.roles;
		for (RoleToken role : roles) {
			Set<String> urls = role.urls;
			if (urls.contains(target)) {
				return true;
			}
			for (String url : urls) {
				if (Pattern.matches(url, target)) {
					return true;
				}
			}
		}
		return false;
	}

	public UserToken createUserToken(HttpServletRequest request) throws Exception {
		UserToken user = new UserToken();
		HttpSession session = request.getSession();
		user.id = session.getId();
		user.host = request.getLocalAddr();
		user.remeberMe = request.getParameter("remeberMe");
		user.userName = request.getParameter("userName");
		user.password = request.getParameter("password");
		return user;
	}

}
