package com.yschoi.demoapp.service.adm;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yschoi.demoapp.dao.adm.AdmLogDao;

/**
* @fileName      : AdmLogService.java
* @author        : YS CHOI
* @date          : 2025.11.18
* @description   : 로깅 Service
* ===========================================================
* DATE           AUTHOR         NOTE
* -----------------------------------------------------------
* 2025.11.18     YS CHOI		최초 생성
*/

@Service
public class AdmLogService {
	@Autowired
	public AdmLogDao admLogDao;
	
	/**
	 * Controller 요청/응답 로그 insert
	 * @param paramMap (USER_IP, USER_ID, URI)
	 */
	@Transactional
	public void insertReqLog(Map<String, Object> paramMap) {
		// 로그 ID 채번
		admLogDao.admLogProc(paramMap);
		
		// 로그 insert
		admLogDao.insertReqLog(paramMap);
	}
}
