package com.tourbest.erp.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p> 공통코드 조회 
 * <p> table : SYS_COMMON_CD_TB
 * 
 * @author shin moon seob
 * @version 1.0, 2018. 12. 28.
 */
@Service("codeUtil")
public class CodeUtil {

	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * 코드 목록 반환
	 * @param commTp
	 * @return
	 */
	public List<CodeUtilVO> selectCodeList(String commTp, HttpServletRequest request)  {
		List<CodeUtilVO> result = new ArrayList<CodeUtilVO>();
		/*CodeUtilVO paramVo = new CodeUtilVO();
		//--------------------------------------------W2UI 인터페이스 설정  :: [필수사항]
		paramVo.__init__(paramVo, request);  // 기본값 설정
		paramVo.__setComInfo__(); // param 기본값 설정
		//---------------------------------------------
		paramVo.setCommTp(commTp);
		CodeUtilDAO codeDao = sqlSession.getMapper(CodeUtilDAO.class);
		result = codeDao.selectCodeList(paramVo);*/		
		return result;
	}
	
	/**
	 * 코드 상위 목록 반환
	 * @param commTp
	 * @param commCdHi
	 * @return
	 */
	public List<CodeUtilVO> selectCodeListHi(String commTp, String commCdHi, HttpServletRequest request)  {
		List<CodeUtilVO> result = new ArrayList<CodeUtilVO>();
		/*CodeUtilVO paramVo = new CodeUtilVO();
		//--------------------------------------------W2UI 인터페이스 설정  :: [필수사항]
		paramVo.__init__(paramVo, request);  // 기본값 설정
		paramVo.__setComInfo__(); // param 기본값 설정
		//---------------------------------------------
		paramVo.setCommTp(commTp);
		paramVo.setCommCdHi(commCdHi);
		
		CodeUtilDAO codeDao = sqlSession.getMapper(CodeUtilDAO.class);
		result = codeDao.selectCodeListHi(paramVo);*/		
		return result;
	}

	
}
