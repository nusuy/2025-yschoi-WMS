package com.yschoi.demoapp.service.adm;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yschoi.demoapp.common.util.ResponseUtil;
import com.yschoi.demoapp.dao.adm.AdmCodeDao;

/**
* @packageName   : com.yschoi.demoapp.service.adm
* @fileName      : AdmCodeService.java
* @author        : YS CHOI
* @date          : 2025.11.20
* @description   : ADM 공통코드 Service
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.20        YS CHOI       최초 생성
*/

@Service
public class AdmCodeService {
	
	@Autowired
	public AdmCodeDao admCodeDao;
	
	/**
	 * 공통코드 헤더 리스트 조회
	 * @param paramMap
	 * @return userMap
	 */
	@Transactional
	public Map<String, Object> selectCodeList(Map<String, Object> paramMap) throws Exception {	
		// result
		HttpStatus status = HttpStatus.OK;
		
		// 데이터 조회
		List<Object> resultList = admCodeDao.selectCodeHeaderList(paramMap);
		
		return ResponseUtil.getResultMap(status, resultList);
	}
	
	/**
	 * 공통코드 헤더 디테일 조회
	 * @param paramMap
	 * @return userMap
	 */
	@Transactional
	public Map<String, Object> selectCodeDetail(Map<String, Object> paramMap) throws Exception {	
		// result
		HttpStatus status = HttpStatus.OK;
		
		// 데이터 조회
		List<Object> resultList = admCodeDao.selectCodeDetail(paramMap);
		
		return ResponseUtil.getResultMap(status, resultList);
	}
	
	/**
	 * 공통코드 신규 등록
	 * @param paramMap
	 * @return userMap
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public Map<String, Object> insertCode(Map<String, Object> paramMap) throws Exception {	
		// 기본 result status
		HttpStatus status = HttpStatus.CREATED;
		String message = "";
		
		// ID 중복체크
		int isExist = admCodeDao.isExistById(paramMap);
		if (isExist > 0) {
			status = HttpStatus.BAD_REQUEST;
			message = "ID already exists.";
		} else {
			// 데이터 등록
			// 헤더
			admCodeDao.insertCodeHeader(paramMap);
			// 디테일
			List<Map<String, Object>> detailList = (List<Map<String, Object>>) paramMap.get("DETAILS");
			if (detailList != null) {
				for (Map<String, Object> detailItem : detailList) {
					detailItem.put("USER_ID", paramMap.get("USER_ID"));
					detailItem.put("CODE_ID", paramMap.get("CODE_ID"));
					admCodeDao.insertCodeDetail(detailItem);
				}
			}
		}
		
		return ResponseUtil.getResultMap(status, message);
	}
}
