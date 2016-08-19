package com.gpdi.searchengine.commonservice.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置文件加载工具包
 * 
 * @author zhangwu
 * from dubbo
 * @version 1.0
 * @created 08-8月-2016 10:08:58
 */
public class ConfigUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(ConfigUtil.class);

	private ConfigUtil() {
	}

	private static volatile Properties PROPERTIES;

	public static void addProperties(Properties properties) {
		if (properties != null) {
			getProperties().putAll(properties);
		}
	}

	public static void setProperties(Properties properties) {
		if (properties != null) {
			PROPERTIES = properties;
		}
	}

	public static Properties getProperties() {
		return PROPERTIES;
	}

	public static String getProperty(String key) {
		return getProperty(key, null);
	}

	public static String getProperty(String key, String defaultValue) {
		return PROPERTIES.getProperty(key, defaultValue);
	}

	public static Properties loadProperties(String fileName) {
		return loadProperties(fileName, false, false);
	}

	public static Properties loadProperties(String fileName,
			boolean allowMultiFile) {
		return loadProperties(fileName, allowMultiFile, false);
	}

	/**
	 * Load properties file to {@link Properties} from class path.
	 * 
	 * @param fileName
	 *            properties file name. for example:
	 *            <code>dubbo.properties</code>,
	 *            <code>METE-INF/conf/foo.properties</code>
	 * @param allowMultiFile
	 *            if <code>false</code>, throw {@link IllegalStateException}
	 *            when found multi file on the class path.
	 * @param optional
	 *            is optional. if <code>false</code>, log warn when properties
	 *            config file not found!s
	 * @return loaded {@link Properties} content.
	 *         <ul>
	 *         <li>return empty Properties if no file found.
	 *         <li>merge multi properties file if found multi file
	 *         </ul>
	 * @throws IllegalStateException
	 *             not allow multi-file, but multi-file exsit on class path.
	 */
	public static Properties loadProperties(String fileName,
			boolean allowMultiFile, boolean optional) {
		Properties properties = new Properties();
		if (fileName.startsWith("/")) {
			try {
				FileInputStream input = new FileInputStream(fileName);
				try {
					properties.load(input);
				} finally {
					input.close();
				}
			} catch (Throwable e) {
				logger.warn("Failed to load " + fileName + " file from "
						+ fileName + "(ingore this file): " + e.getMessage(), e);
			}
			return properties;
		}

		List<java.net.URL> list = new ArrayList<java.net.URL>();
		try {
			Enumeration<java.net.URL> urls = ClassHelper.getClassLoader()
					.getResources(fileName);
			list = new ArrayList<java.net.URL>();
			while (urls.hasMoreElements()) {
				list.add(urls.nextElement());
			}
		} catch (Throwable t) {
			logger.warn(
					"Fail to load " + fileName + " file: " + t.getMessage(), t);
		}

		if (list.size() == 0) {
			if (!optional) {
				logger.warn("No " + fileName + " found on the class path.");
			}
			return properties;
		}

		if (!allowMultiFile) {
			if (list.size() > 1) {
				String errMsg = String
						.format("only 1 %s file is expected, but %d dubbo.properties files found on class path: %s",
								fileName, list.size(), list.toString());
				logger.warn(errMsg);
				// throw new IllegalStateException(errMsg); // see
				// http://code.alibabatech.com/jira/browse/DUBBO-133
			}

			// fall back to use method getResourceAsStream
			try {
				properties.load(ClassHelper.getClassLoader()
						.getResourceAsStream(fileName));
			} catch (Throwable e) {
				logger.warn("Failed to load " + fileName + " file from "
						+ fileName + "(ingore this file): " + e.getMessage(), e);
			}
			return properties;
		}

		logger.info("load " + fileName + " properties file from " + list);

		for (java.net.URL url : list) {
			try {
				Properties p = new Properties();
				InputStream input = url.openStream();
				if (input != null) {
					try {
						p.load(input);
						properties.putAll(p);
					} finally {
						try {
							input.close();
						} catch (Throwable t) {
						}
					}
				}
			} catch (Throwable e) {
				logger.warn("Fail to load " + fileName + " file from " + url
						+ "(ingore this file): " + e.getMessage(), e);
			}
		}

		return properties;
	}
}