package com.smoopay.sts.services.bank.dao;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.text.ParseException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.mysema.query.types.expr.BooleanExpression;
import com.smoopay.sts.AbstractIntegrationTest;
import com.smoopay.sts.entity.common.account.BankAccount;
import com.smoopay.sts.entity.common.account.QBankAccount;
import com.smoopay.sts.repository.bank.BankAccountRepository;

/**
 * Integration tests for {@link BankAccountRepository}.
 * 
 * @author Adam Dec
 */
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class BankAccountRepositoryIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Test
	public void shouldRetunBankAccountByGivenName() throws ParseException {
		// given
		final String bankName = "Alior Bank";
		QBankAccount bankAcc = QBankAccount.bankAccount;
		BooleanExpression name = bankAcc.bankAccountData.bankName.eq(bankName);

		// when
		BankAccount bankAccount = bankAccountRepository.findOne(name);

		// than
		assertThat(bankAccount, is(notNullValue()));
	}
}