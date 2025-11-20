package com.yschoi.demoapp.controller.adm;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yschoi.demoapp.service.adm.AdmLoginService;

/**
* @packageName   : com.yschoi.demoapp.controller.adm
* @fileName      : AdmLoginController.java
* @author        : YS CHOI
* @date          : 2025.11.20
* @description   : ADM 로그인 Controller
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.20        YS CHOI       최초 생성
*/

@RequestMapping("/api/v1/adm")
@RestController
public class AdmLoginController {
	@Autowired
	public AdmLoginService admLoginService;
	
	@PostMapping("/login")
	public Map<String, Object> admLogin(@RequestBody Map<String, Object> bodyParam) throws Exception {
		return admLoginService.login(bodyParam);
	}
}
