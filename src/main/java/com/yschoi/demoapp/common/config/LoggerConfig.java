package com.yschoi.demoapp.common.config;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

import com.yschoi.demoapp.common.util.CommonUtil;
import com.yschoi.demoapp.service.adm.AdmLogService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
* @packageName   : com.yschoi.demoapp.common.config
* @fileName      : LoggerConfig.java
* @author        : YS CHOI
* @date          : 2025.11.20
* @description   : API 요청 로깅을 위한 AOP
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.20        YS CHOI       최초 생성
*/

@Slf4j
@Component
@Aspect
public class LoggerConfig {
	@Autowired
	public AdmLogService admLogService;

	@Pointcut("execution(* com.yschoi.demoapp.controller.*.*Controller.*(..))")
	public void controllerPointcut() {}
	
	@Pointcut("within(@org.springframework.web.bind.annotation.RestControllerAdvice *)")
	public void annotationPointcut() {}

	@SuppressWarnings("unchecked")
	@Around("controllerPointcut() || annotationPointcut()")
	public Object printLog(ProceedingJoinPoint pjp) throws Throwable {
		log.info("*****LoggingAspect*****");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		Map<String, Object> paramMap = new HashMap<>();
		
		// Controller Path (ExceptionHandler를 통한 로깅이어도 원래 요청 받은 Controller 기록)
		String controllerPath = "";
		Object handler = request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
		if (handler instanceof HandlerMethod handlerMethod) {
			controllerPath = handlerMethod.getBeanType().getName().replace("com.lkictdev.demoapp.controller.", "");
		}

		try {
			String decodedUri = URLDecoder.decode(request.getRequestURI(), "UTF-8");
			
			// Springboot 자동 에러 포워딩 - 로그 제외
			if ("/error".equals(decodedUri))
				return pjp.proceed();

			paramMap.put("SERVICE_NM", controllerPath);
			paramMap.put("URI", decodedUri);
			paramMap.put("HTTP_METHOD", request.getMethod());
			paramMap.put("REQ_PARAM", CommonUtil.subStrLen(request.getAttribute("CACHED_BODY").toString(), 255));
			paramMap.put("USER_IP", request.getRemoteAddr());
			paramMap.put("USER_LANG", request.getHeader("USER_LANG"));
		} catch (Exception e) {
			log.error("LoggerAspect error", e);
		}

		try {
			// Interceptor 거쳐서 저장된 사용자 정보
			Map<String, Object> userMap = (Map<String, Object>) request.getAttribute("USER_MAP");
			if (userMap != null)
				paramMap.put("USER_ID", userMap.get("USER_ID"));
			
			// 컨트롤러 진행
			Object proceed = pjp.proceed();
			HashMap<String, Object> resultMap = (HashMap<String, Object>) proceed;
			
			paramMap.put("RES_CODE", resultMap.get("status"));
			paramMap.put("EXCEPTION_MSG", resultMap.get("message"));
			paramMap.put("RES_PARAM", CommonUtil.subStrLen(resultMap.get("data").toString(), 255));
						
			return proceed;
		} catch (Exception e) {
			log.info(e.getMessage());
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			
			paramMap.put("EXCEPTION_MSG", e.getMessage());
			paramMap.put("ERROR_TRACE", CommonUtil.subStrLen(sw.toString(), 255));
			
			throw e;
		} finally {
			admLogService.insertReqLog(paramMap);
		}
	}
}
