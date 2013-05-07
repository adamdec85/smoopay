package com.smoopay.sts.schedule;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.smoopay.sts.entity.common.account.BankAccount;
import com.smoopay.sts.repository.bank.BankAccountRepository;
import com.smoopay.sts.utils.CollectionUtils;

public class SimpleJobWithAutowiring extends QuartzJobBean {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Autowired
	private CollectionUtils collectionUtils;

	@Autowired
	protected ApplicationContext applicationContext;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		if (logger.isDebugEnabled()) {
			logger.debug("--->>> Smoopay job executed...");
		}
		List<BankAccount> bankAccounts = collectionUtils.copyIterator(bankAccountRepository.findAll().iterator());
		logger.info("bankAccounts=" + bankAccounts);
	}
}