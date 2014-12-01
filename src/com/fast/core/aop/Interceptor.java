package com.fast.core.aop;

import com.fast.core.Action;

public interface Interceptor {

	public void intercept(Action action);
}
