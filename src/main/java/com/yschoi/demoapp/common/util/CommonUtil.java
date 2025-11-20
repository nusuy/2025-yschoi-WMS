package com.yschoi.demoapp.common.util;

/**
* @packageName   : com.yschoi.demoapp.common.util
* @fileName      : CommonUtil.java
* @author        : YS CHOI
* @date          : 2025.11.20
* @description   : 공통 Utility
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.20        YS CHOI       최초 생성
*/

public class CommonUtil {
	
	/**
	 * 문자열 길이 제한
	 * @author YS CHOI
	 * @date   2025. 11. 18.
	 * @param str, maxLen
	 * @return
	 */
	static public String subStrLen(String str, Integer maxLen) {
		return str.length() >= maxLen ? str.substring(0, maxLen) : str;
	}
}
