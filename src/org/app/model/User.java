package org.app.model;

import com.fast.annotation.Model;
import com.fast.core.base.BaseModel;

@Model(name = "cms_user")
public class User extends BaseModel {

<<<<<<< HEAD
=======
	private static final long serialVersionUID = -6331857939199025230L;
>>>>>>> 759fbe3f1537220d55188f6ae27c99e6cdcc5b5d
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
