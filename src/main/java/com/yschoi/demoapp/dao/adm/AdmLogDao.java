package com.yschoi.demoapp.dao.adm;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
* @fileName      : AdmLogDao.java
* @author        : YS CHOI
* @date          : 2025.11.18
* @description   : 로깅 DAO
* ===========================================================
* DATE           AUTHOR         NOTE
* -----------------------------------------------------------
* 2025.11.18     YS CHOI		최초 생성
*/

@Repository
public class AdmLogDao {
	
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 로그 ID 채번
	 */
	public int admLogProc(Map<String, Object> paramMap) {
		return sqlSession.insert("ADM_LOG_PROC", paramMap);
	}

	/**
	 * API 요청 기록 로그 insert
	 */
	public int insertReqLog(Map<String, Object> paramMap) {
		return sqlSession.insert("ADM_LOG_INSERT_REQ", paramMap);
	}
	

}
