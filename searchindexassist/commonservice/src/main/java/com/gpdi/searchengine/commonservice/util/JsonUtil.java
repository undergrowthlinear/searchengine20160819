package com.gpdi.searchengine.commonservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json工具类
 * 
 * @author zhangwu
 * @version 1.0
 * @created 08-8月-2016 10:08:58
 */
public class JsonUtil {

	private JsonUtil() {

	}

	private static final ObjectMapper mapper = new ObjectMapper();

	public static String serialize(Object obj) throws Exception {
		return mapper.writeValueAsString(obj);
	}

	public static <T> T deserialize(String src, Class<T> t) throws Exception {
		if (src == null) {
			throw new IllegalArgumentException("src should not be null");
		}
		return mapper.readValue(src, t);
	}

}