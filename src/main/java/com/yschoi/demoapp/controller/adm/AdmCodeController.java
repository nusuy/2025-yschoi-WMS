package com.yschoi.demoapp.controller.adm;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yschoi.demoapp.service.adm.AdmCodeService;

import jakarta.servlet.http.HttpServletRequest;

/**
* @packageName   : com.yschoi.demoapp.controller.adm
* @fileName      : AdmCodeController.java
* @author        : YS CHOI
* @date          : 2025.11.20
* @description   : ADM 공통코드 Controller
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.20        YS CHOI       최초 생성
*/

@RequestMapping("/api/v1/adm/code")
@RestController
public class AdmCodeController {
	@Autowired
	public AdmCodeService admCodeService;

	@PostMapping("/**")
	public Map<String, Object> admCode(HttpServletRequest request, @RequestBody Map<String, Object> bodyParam) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		String reqUri = request.getRequestURI().replace("/api/v1/adm/code", "");
		bodyParam.put("USER_IP", request.getRemoteAddr());
		
		if ("".equals(reqUri)) {
			resultMap = admCodeService.selectCodeList(bodyParam);	// 공통코드 헤더 리스트 조회
		} else if ("/detail".equals(reqUri)) {
			resultMap = admCodeService.selectCodeDetail(bodyParam);	// 공통코드 디테일 조회
		} else if ("/new".equals(reqUri)) {
			resultMap = admCodeService.insertCode(bodyParam);		// 공통코드 신규 등록			
		}
		
		return resultMap;
	}	
}
