package org.app.aop;

import com.fast.core.Action;
import com.fast.core.aop.Interceptor;

public class TestLog implements Interceptor {

	@Override
	public void intercept(Action action) {
		System.out.println("========Before==========" + action.getControllerClass() + "  " + action.getViewPath());
	}

}
