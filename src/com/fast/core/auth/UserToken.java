package com.fast.core.auth;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserToken {

	public static final String TOKEN_USERNAME = "userName";

	public Serializable id;
	public String host;
	public String remeberMe;
	public String userName;
	public String password;
	public Set<RoleToken> roles = new HashSet<RoleToken>();

}
