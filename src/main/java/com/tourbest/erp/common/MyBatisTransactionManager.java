package com.tourbest.erp.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @Class Name : MyBatisTransactionManager
 * @Description : 마이바티스 트랜젝션 매니저
 *
 * @author
 * @since
 */
@Service
@Scope("prototype")
public class MyBatisTransactionManager extends DefaultTransactionDefinition {

	/**서블릿 UID*/
	private static final long serialVersionUID = 7407829348017696779L;

	/**로거 */
	protected static final Logger logger = LoggerFactory.getLogger(MyBatisTransactionManager.class);

	/**트랜젝션 매니저*/
	@Autowired
	@Qualifier("transactionManager")
	PlatformTransactionManager transactionMgr;

	/**트랜젝션 상태*/
	TransactionStatus status;

	/**
	 * 트랜젝션 스타트
	 * @throws TransactionException
	 */
	public void start() throws TransactionException {
		status = transactionMgr.getTransaction(this);
		logger.info("MyBatisTransaction START");
	}

	/**
	 * 커밋처리
	 * @throws TransactionException
	 */
	public void commit() throws TransactionException {
		if (!status.isCompleted()) {
			transactionMgr.commit(status);
			logger.info("MyBatisTransaction COMMIT");
		}
	}

	/**
	 * 롤백처리
	 * @throws TransactionException
	 */
	public void rollback() throws TransactionException {
		if (!status.isCompleted()) {
			transactionMgr.rollback(status);
			logger.info("MyBatisTransaction ROLLBACK");
		}
	}

	/**
	 * 트랜젝션 종료
	 * @throws TransactionException
	 */
	public void end() throws TransactionException {
		rollback();
		logger.info("MyBatisTransaction END");
	}
}