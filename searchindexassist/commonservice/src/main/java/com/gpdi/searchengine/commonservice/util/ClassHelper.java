package com.gpdi.searchengine.commonservice.util;

/**
 * 
* @description: TODO(这里用一句话描述这个类的作用)
* from dubbo
* @author zhangwu
* @date 2016年8月12日
* @version 1.0.0
 */
public class ClassHelper {
	/**
	 * get class loader
	 * 
	 * @param cls
	 * @return class loader
	 */
	public static ClassLoader getClassLoader(Class<?> cls) {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
			// Cannot access thread context ClassLoader - falling back to system
			// class loader...
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = cls.getClassLoader();
		}
		return cl;
	}

	/**
	 * Return the default ClassLoader to use: typically the thread context
	 * ClassLoader, if available; the ClassLoader that loaded the ClassUtils
	 * class will be used as fallback.
	 * <p>
	 * Call this method if you intend to use the thread context ClassLoader in a
	 * scenario where you absolutely need a non-null ClassLoader reference: for
	 * example, for class path resource loading (but not necessarily for
	 * <code>Class.forName</code>, which accepts a <code>null</code> ClassLoader
	 * reference as well).
	 * 
	 * @return the default ClassLoader (never <code>null</code>)
	 * @see java.lang.Thread#getContextClassLoader()
	 */
	public static ClassLoader getClassLoader() {
		return getClassLoader(ClassHelper.class);
	}
}
