package com.yschoi.demoapp.dao.adm;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
* @packageName   : com.yschoi.demoapp.dao.adm
* @fileName      : AdmLoginDao.java
* @author        : YS CHOI
* @date          : 2025.11.20
* @description   : ADM 로그인 DAO
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.20        YS CHOI       최초 생성
*/

@Repository
public class AdmLoginDao {
	
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 세션 ID 채번
	 */
	public int admSessionProc(Map<String, Object> paramMap) {
		return sqlSession.insert("ADM_SESSION_PROC", paramMap);
	}
	
	/**
	 * 로그인
	 */
	public Map<String, Object> login(Map<String, Object> paramMap) {
		return sqlSession.selectOne("ADM_LOGIN_PW", paramMap);
	}

	/**
	 * 마지막 로그인 일시 갱신 및 세션 등록
	 */
	public int updateLoginSession(Map<String, Object> paramMap) {
		return sqlSession.update("ADM_UPDATE_LOGIN_SESSION", paramMap);
	}
}
