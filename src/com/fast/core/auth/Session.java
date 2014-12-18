package com.fast.core.auth;

import java.io.Serializable;

public interface Session {

	public Serializable getId();

	public String getHost();

	public void stop();

}
