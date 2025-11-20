package com.yschoi.demoapp.dao.adm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
* @packageName   : com.yschoi.demoapp.dao.adm
* @fileName      : AdmCodeDao.java
* @author        : YS CHOI
* @date          : 2025.11.20
* @description   : ADM 공통코드 DAO
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.20        YS CHOI       최초 생성
*/

@Repository
public class AdmCodeDao {
	
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 공통코드 헤더 리스트 조회
	 */
	public List<Object> selectCodeHeaderList(Map<String, Object> paramMap) {
		return sqlSession.selectList("ADM_CODE_SELECT_HEADER_LIST", paramMap);
	}
	
	/**
	 * 공통코드 디테일 리스트 조회
	 */
	public List<Object> selectCodeDetail(Map<String, Object> paramMap) {
		return sqlSession.selectList("ADM_CODE_SELECT_DETAIL", paramMap);
	}

	
	/**
	 * 공통코드 ID 중복확인
	 */
	public int isExistById(Map<String, Object> paramMap) {
		return sqlSession.selectOne("ADM_CODE_CHK_ID", paramMap);
	}

	
	/**
	 * 공통코드 헤더 등록
	 */
	public int insertCodeHeader(Map<String, Object> paramMap) {
		return sqlSession.insert("ADM_CODE_INSERT_NEW_HEDAER", paramMap);
	}
	
	/**
	 * 공통코드 디테일 등록
	 */
	public int insertCodeDetail(Map<String, Object> paramMap) {
		return sqlSession.update("ADM_CODE_INSERT_DETAIL", paramMap);
	}

}
