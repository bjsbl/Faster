package com.fast.log;

public interface LoggerFactory {

	Logger getLogger(Class<?> clazz);

	Logger getLogger(String clazz);
}
