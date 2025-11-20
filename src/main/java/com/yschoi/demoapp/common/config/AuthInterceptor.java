package com.yschoi.demoapp.common.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.HandlerInterceptor;

import com.yschoi.demoapp.common.RequestWrapper;
import com.yschoi.demoapp.service.adm.AdmLoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
* @packageName   : com.yschoi.demoapp.common.config
* @fileName      : AuthInterceptor.java
* @author        : YS CHOI
* @date          : 2025.11.20
* @description   : 사용자 검증 Interceptor
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.20        YS CHOI       최초 생성
*/

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {
	@Autowired
	public AdmLoginService admLoginService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("======= AuthInterceptor IN =======");
		
		// Request Wrapping
		RequestWrapper wrappedRequest = new RequestWrapper(request);
		wrappedRequest.setAttribute("CACHED_BODY", wrappedRequest.getCachedBody());
		
		String sessionId = request.getHeader("SESSION_ID");
		if ("".equals(sessionId))
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "SESSION ID REQUIRED");

		// 세션 ID 검증
		Map<String, Object> resultMap = admLoginService.verifySessionId(sessionId);
		request.getSession().setAttribute("USER_MAP", resultMap);
		
		if (resultMap == null) {
			// 세션 정보 찾을 수 없음
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "SESSION ID VERIFICATION FAILED");
		} else if ("1".equals(resultMap.get("IS_EXPIRED"))) {
			// 세션 만료
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "SESSION EXPIRED");
		}


		log.info("======= AuthInterceptor OUT =======");
		return HandlerInterceptor.super.preHandle(wrappedRequest, response, handler);
	}
}
