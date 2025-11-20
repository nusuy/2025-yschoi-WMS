package com.yschoi.demoapp.common.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

/**
* @packageName  : com.yschoi.demoapp.common.util
* @fileName     : ResponseUtil.java
* @author       : YS CHOI
* @date         : 2025.11.20
* @description  : API 응답 Utility
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.20        YS CHOI       최초 생성
*/

public class ResponseUtil {
	/**
	 * ResultMap 반환
	 * 
	 * @author YS CHOI
	 * @date   2025. 11. 07.
	 * @param status, message
	 * @return
	 */
	public static Map<String, Object> getResultMap(HttpStatus status) {
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("status", status.value());
		resultMap.put("message", status.getReasonPhrase());
		
		return resultMap;
	}
	
	/**
	 * ResultMap 반환 (메세지 설정)
	 * 
	 * @author YS CHOI
	 * @date   2025. 11. 07.
	 * @param status, message
	 * @return
	 */
	public static Map<String, Object> getResultMap(HttpStatus status, String message) {
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("status", status.value());
		resultMap.put("message", message);
		
		return resultMap;
	}
	
	/**
	 * ResultMap 반환 (status code, 메세지 설정)
	 * 
	 * @author YS CHOI
	 * @date   2025. 11. 07.
	 * @param status, message
	 * @return
	 */
	public static Map<String, Object> getResultMap(HttpStatusCode statusCode, String message) {
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("status", statusCode.value());
		resultMap.put("message", message);
		
		return resultMap;
	}
	
	/**
	 * Data 포함한 ResultMap 반환
	 * 
	 * @author YS CHOI
	 * @date   2025. 11. 07.
	 * @param status, message, data
	 * @return
	 */
	public static Map<String, Object> getResultMap(HttpStatus status, Object data) {
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("status", status.value());
		resultMap.put("message", status.getReasonPhrase());
		resultMap.put("data", data);
		
		return resultMap;
	}
	
	/**
	 * Data 포함한 ResultMap 반환 (메세지 설정)
	 * 
	 * @author YS CHOI
	 * @date   2025. 11. 07.
	 * @param status, message, data
	 * @return
	 */
	public static Map<String, Object> getResultMap(HttpStatus status, String message, Object data) {
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("status", status.value());
		resultMap.put("message", message);
		resultMap.put("data", data);
		
		return resultMap;
	}
}
