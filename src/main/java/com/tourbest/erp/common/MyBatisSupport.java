package com.tourbest.erp.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @Class Name : MyBatisSupport
 * @Description : 마이바티스 트랙젝션 처리를 위한 서포트 클래스
 *
 * @author
 * @since
 */
@Service("myBatisSupport")
public class MyBatisSupport {

	/**로거 */
	private static final Logger logger = LoggerFactory.getLogger(MyBatisSupport.class);

	/**세션 */
	@Autowired(required = false)
	@Qualifier("sqlSession")
	protected MyBatisSqlSessionTemplate sqlSession;

	@Autowired
	ApplicationContext applicationContext;

	/**
	 * 스프링 ApplicationContext 관리 할당
	 * @return
	 */
	public MyBatisTransactionManager getTransactionManager() {
		return applicationContext.getBean(MyBatisTransactionManager.class);
	}

}