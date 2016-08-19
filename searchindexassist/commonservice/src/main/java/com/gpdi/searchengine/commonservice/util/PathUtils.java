package com.gpdi.searchengine.commonservice.util;

/**
 * This class is copied from Apache ZooKeeper. The original class is not
 * exported by ZooKeeper bundle and thus it can't be used in OSGi. See issue:
 * https://issues.apache.org/jira/browse/ZOOKEEPER-1627 A temporary workaround
 * till the issue is resolved is to keep a copy of this class locally.
 */
public class PathUtils {

	/**
	 * validate the provided znode path string
	 * 
	 * @param path
	 *            znode path string
	 * @param isSequential
	 *            if the path is being created with a sequential flag
	 * @throws IllegalArgumentException
	 *             if the path is invalid
	 */
	public static boolean validatePath(String path, boolean isSequential)
			throws IllegalArgumentException {
		return validatePath(isSequential ? path + "1" : path);
	}

	/**
	 * Validate the provided znode path string
	 * 
	 * @param path
	 *            znode path string
	 * @throws IllegalArgumentException
	 *             if the path is invalid
	 */
	public static boolean validatePath(String path) {
		if (path == null) {
			return false;
		}
		if (path.length() == 0) {
			return false;
		}
		if (path.charAt(0) != '/') {
			return false;
		}
		if (path.length() == 1) { // done checking - it's the root
			return true;
		}
		if (path.charAt(path.length() - 1) == '/') {
			return false;
		}

		String reason = null;
		char lastc = '/';
		char chars[] = path.toCharArray();
		char c;
		for (int i = 1; i < chars.length; lastc = chars[i], i++) {
			c = chars[i];

			if (c == 0) {
				reason = "null character not allowed @" + i;
				break;
			} else if (c == '/' && lastc == '/') {
				reason = "empty node name specified @" + i;
				break;
			} else if (c == '.' && lastc == '.') {
				if (chars[i - 2] == '/'
						&& ((i + 1 == chars.length) || chars[i + 1] == '/')) {
					reason = "relative paths not allowed @" + i;
					break;
				}
			} else if (c == '.') {
				if (chars[i - 1] == '/'
						&& ((i + 1 == chars.length) || chars[i + 1] == '/')) {
					reason = "relative paths not allowed @" + i;
					break;
				}
			} else if (c > '\u0000' && c < '\u001f' || c > '\u007f'
					&& c < '\u009F' || c > '\ud800' && c < '\uf8ff'
					|| c > '\ufff0' && c < '\uffff') {
				reason = "invalid charater @" + i;
				break;
			}
		}

		if (reason != null) {
			return false;
		}
		return true;
	}
}
