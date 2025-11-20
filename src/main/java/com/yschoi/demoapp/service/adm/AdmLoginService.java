package com.yschoi.demoapp.service.adm;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yschoi.demoapp.common.util.ResponseUtil;
import com.yschoi.demoapp.dao.adm.AdmLoginDao;

/**
* @packageName   : com.yschoi.demoapp.service.adm
* @fileName      : AdmLoginService.java
* @author        : YS CHOI
* @date          : 2025.11.20
* @description   : ADM 로그인 Service
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.20        YS CHOI       최초 생성
*/

@Service
public class AdmLoginService {
	
	@Autowired
	public AdmLoginDao admLoginDao;
	
	/**
	 * 로그인
	 * @param paramMap (USER_IP, USER_ID, PASSWORD)
	 * @return userMap (USER_ID, USER_NM, USE_YN, SESSION_ID)
	 */
	@Transactional
	public Map<String, Object> login(Map<String, Object> paramMap) throws Exception {	
		// result
		HttpStatus status = HttpStatus.OK;
		
		// PW 일치여부 확인
		Map<String, Object> userMap = admLoginDao.login(paramMap);
		
		if (userMap == null) {
			// 아이디 미존재, 비밀번호 오류
			status = HttpStatus.UNAUTHORIZED;
		} else if ("N".equals(userMap.get("USE_YN"))) {
			// 사용 중지된 사용자
			status = HttpStatus.FORBIDDEN;
		} else {
			// 로그인 성공
			
			// 세션 ID 채번
			admLoginDao.admSessionProc(paramMap);
			
			// 마지막 로그인 일시 갱신 및 세션 등록
			admLoginDao.updateLoginSession(paramMap);
			
			// 응답 데이터에 세션 ID 추가
			userMap.put("SESSION_ID", paramMap.get("SESSION_ID"));
		}
		
		return ResponseUtil.getResultMap(status, userMap);
	}

	/**
	 * 세션 검증
	 * @param sessionId
	 * @return resultMap (USER_ID, IS_EXPIRED)
	 */
	// TODO
	
	/**
	 * 로그아웃
	 * @param paramMap
	 * @return
	 */	
	// TODO
	
	/**
	 * 회원추가
	 * @param paramMap
	 * @return
	 */
	// TODO
	
	
}
