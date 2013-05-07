package com.smoopay.sts.services.bank.dao;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.smoopay.sts.AbstractIntegrationTest;
import com.smoopay.sts.common.dto.CurrencyEnum;
import com.smoopay.sts.entity.bank.Bank;
import com.smoopay.sts.entity.bank.QBank;
import com.smoopay.sts.entity.client.QClient;
import com.smoopay.sts.entity.common.Currency;
import com.smoopay.sts.entity.common.account.BankAccount;
import com.smoopay.sts.entity.common.account.ClientAccount;
import com.smoopay.sts.entity.common.account.QBankAccount;
import com.smoopay.sts.entity.common.account.QClientAccount;
import com.smoopay.sts.repository.bank.BankAccountRepository;
import com.smoopay.sts.repository.bank.BankReadOnlyRepository;
import com.smoopay.sts.repository.bank.BankRepository;
import com.smoopay.sts.repository.client.ClientAccountRepository;

/**
 * Integration tests for {@link BankRepository}.
 * 
 * @author Adam Dec
 */
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class BankRepositoryIntegrationTest extends AbstractIntegrationTest {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private BankReadOnlyRepository bankReadOnlyRepository;

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Autowired
	private ClientAccountRepository clientAccountRepository;

	@Test
	public void shouldRetunAllBanks() throws ParseException {
		// when
		List<Bank> banks = bankReadOnlyRepository.findAll();

		// than
		assertThat(banks, is(notNullValue()));
		assertThat(banks.size(), is(2));
	}

	@Test
	public void shouldTake100FromAdamPolishAccountAndDebitAlior() {
		// given
		JPAQuery query = null;
		QClient client = QClient.client;
		QClientAccount clientAccount = QClientAccount.clientAccount;
		QBank bank = QBank.bank;
		QBankAccount bankAccount = QBankAccount.bankAccount;
		BooleanExpression firstName = client.firstName.startsWith("Adam");
		BooleanExpression currency = clientAccount.currency.eq(new Currency(CurrencyEnum.PLN));

		// when
		query = new JPAQuery(em);
		ClientAccount clientAcc = query.from(clientAccount).join(clientAccount.client, client).fetch().where(firstName, currency).singleResult(clientAccount);
		assertThat(client, is(notNullValue()));

		query = new JPAQuery(em);
		BooleanExpression name = bank.name.startsWith("Alior");
		BankAccount bankAcc = query.from(bankAccount).join(bankAccount.bank, bank).fetch().where(name).singleResult(bankAccount);
		assertThat(bankAcc, is(notNullValue()));

		BigDecimal clientBalance = clientAcc.getClientFinancialBalance().getPrimaryAccountBalance();
		clientAcc.getClientFinancialBalance().setPrimaryAccountBalance(BigDecimal.ZERO);
		bankAcc.getBankFinancialBalance().setPrimaryAccountBalance(bankAcc.getBankFinancialBalance().getPrimaryAccountBalance().add(clientBalance));

		clientAcc = clientAccountRepository.save(clientAcc);
		bankAcc = bankAccountRepository.save(bankAcc);

		// than
		assertThat(clientAcc.getClientFinancialBalance().getPrimaryAccountBalance(), is(BigDecimal.ZERO));
		assertThat(bankAcc.getBankFinancialBalance().getPrimaryAccountBalance().doubleValue(), is(new BigDecimal("100.0").doubleValue()));
	}
}