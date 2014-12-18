package org.app.model;

import com.fast.annotation.Model;
import com.fast.core.base.BaseModel;

@Model(name = "cms_user")
public class User extends BaseModel {

	public String id;
	public String userName;
	public String password;

}
