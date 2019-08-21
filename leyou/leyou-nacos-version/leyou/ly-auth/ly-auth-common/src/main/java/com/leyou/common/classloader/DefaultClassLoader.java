package com.leyou.common.classloader;

public class DefaultClassLoader {

	public static ClassLoader getDefaultClassLoader() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if(classLoader == null) {
			classLoader = DefaultClassLoader.class.getClassLoader();
		}
		return classLoader;
	}
}
