package com.smoopay.sts.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import com.smoopay.sts.entity.common.account.BankAccount;

@Service
public class BankRepositoryProcessor implements ItemProcessor<BankAccount, String> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public String process(BankAccount bankAccount) {
		if (logger.isDebugEnabled()) {
			logger.debug("Processing bank account with id=" + bankAccount.getId());
		}
		return bankAccount.getVirtualAccNo() + "|" + bankAccount.getBankAccountData().getCashAccNRB() + "|"
				+ bankAccount.getBankFinancialBalance().getLockedBalance();
	}
}