package com.tourbest.erp.common;

import java.util.List;


public interface CodeUtilDAO {	
	
	/**
	 * 
	 * @return
	 * 
	 * @param CodeDAO
	 * @param 
	 * @exception
	 */
	public List<CodeUtilVO> selectCodeList(CodeUtilVO paramVo);	
	
	
	public List<CodeUtilVO> selectCodeListHi(CodeUtilVO paramVo);


		
	
	
}
