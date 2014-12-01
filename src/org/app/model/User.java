package org.app.model;

import com.fast.annotation.Model;
import com.fast.core.base.BaseModel;

@Model(name = "cms_user")
public class User extends BaseModel {

	private String id;
	private String name;
	private int orderIndex;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

}
