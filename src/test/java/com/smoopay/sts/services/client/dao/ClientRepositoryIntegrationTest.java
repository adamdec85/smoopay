package com.smoopay.sts.services.client.dao;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.hamcrest.Matchers;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.mysema.query.jpa.hibernate.HibernateQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.smoopay.sts.AbstractIntegrationTest;
import com.smoopay.sts.common.dto.CurrencyEnum;
import com.smoopay.sts.common.dto.account.AccountStatusEnum;
import com.smoopay.sts.dao.client.ClientCustomRepository;
import com.smoopay.sts.entity.client.Client;
import com.smoopay.sts.entity.client.QClient;
import com.smoopay.sts.entity.common.AuthData;
import com.smoopay.sts.entity.common.Currency;
import com.smoopay.sts.entity.common.EmailAddress;
import com.smoopay.sts.entity.common.ClientFinancialBalance;
import com.smoopay.sts.entity.common.account.ClientAccount;
import com.smoopay.sts.entity.common.account.QClientAccount;
import com.smoopay.sts.entity.common.account.data.ClientAccountData;
import com.smoopay.sts.entity.common.account.status.AccountStatus;
import com.smoopay.sts.entity.common.address.ClientAddress;
import com.smoopay.sts.repository.client.ClientAccountRepository;
import com.smoopay.sts.repository.client.ClientRepository;
import com.smoopay.sts.repository.common.AccountDataRepository;
import com.smoopay.sts.repository.common.AddressRepository;
import com.smoopay.sts.repository.common.FinancialBalanceRepository;
import com.smoopay.sts.services.account.client.response.ClientAccount_1_0;

/**
 * Integration tests for {@link ClientRepository}.
 * 
 * http://docs.jboss.org/hibernate/orm/3.3/reference/en/html/querycriteria.html
 * http://www.tutorialspoint.com/hibernate/hibernate_batch_processing.htm
 * 
 * @author Adam Dec
 */
public class ClientRepositoryIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientCustomRepository clientCustomRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ClientAccountRepository clientAccountRepository;

	@Autowired
	private AccountDataRepository accountDataRepository;

	@Autowired
	private FinancialBalanceRepository financialBalanceRepository;

	@Test
	public void shouldGetClientByLogin() {
		// given
		final String login = "adam1_login";

		// when
		Client client = clientCustomRepository.findClientByLogin(login);

		// than
		assertThat(client, is(notNullValue()));
		assertThat(client.getFirstName(), is("Adam"));
		assertThat(client.getLastName(), is("Dec"));
	}

	@Test
	public void shouldNotGetClientByLogin() {
		// given
		final String login = "adam3_login";

		// when
		Client client = clientCustomRepository.findClientByLogin(login);

		// than
		assertThat(client, is(nullValue()));
	}

	@Test
	public void shouldCreateNewAccountForClient() {
		// given
		final Long clientId = 1L;
		ClientAccount clientAccount = new ClientAccount("1", new AccountStatus(AccountStatusEnum.ACTIVE), new Currency(CurrencyEnum.valueOf("PLN")));
		ClientAccountData accountData = new ClientAccountData("bank", "1", "PL 1");
		clientAccount.setClientAccountData(accountData);
		ClientFinancialBalance financialBalance = new ClientFinancialBalance(BigDecimal.ZERO);
		clientAccount.setClientFinancialBalance(financialBalance);
		ClientAccount savedClientAccount = clientAccountRepository.save(clientAccount);
		QClient client = QClient.client;
		BooleanExpression clientIdPredicate = client.id.eq(clientId);

		// when
		Client c = clientRepository.findOne(clientIdPredicate);
		c.add(savedClientAccount);
		clientRepository.save(c);

		// than
		assertThat(c.getClientAccounts().size(), is(3));
	}

	@Test
	public void shouldGetAllAccountsForClient() {
		// given
		final Long clientId = 1L;

		// when
		List<ClientAccount_1_0> clientAccounts = clientCustomRepository.findAllClientAccountsByClientId(clientId);

		// than
		assertThat(clientAccounts.size(), is(2));
	}

	@Test
	public void shouldGetAllAccountsByCurrencyForClient() {
		// given
		final Long clientId = 1L;
		final String currency = "PLN";

		// when
		List<ClientAccount_1_0> clientAccounts = clientCustomRepository.findClientAccountsByCurrency(clientId, currency);

		// than
		assertThat(clientAccounts.size(), is(1));
	}

	@Test
	public void shouldGetAllAccountsByStatusForClient() {
		// given
		final Long clientId = 1L;
		final String status = "ACTIVE";

		// when
		List<ClientAccount_1_0> clientAccounts = clientCustomRepository.findClientAccountsByStatus(clientId, status);

		// than
		assertThat(clientAccounts.size(), is(2));
	}

	@Test
	public void shouldGetAllAccountsByVirtualAccNumberForClient() {
		// given
		final Long clientId = 1L;
		final String virtualNumber = "1-Alior Bank";

		// when
		List<ClientAccount_1_0> clientAccounts = clientCustomRepository.findClientAccountsByVirtualAccNo(clientId, virtualNumber);

		// than
		assertThat(clientAccounts.size(), is(1));
	}

	@Test
	public void findsClientById() {
		// when
		Client customer = clientRepository.findOne(1L);

		// than
		assertThat(customer, is(notNullValue()));
		assertThat(customer.getFirstName(), is("Adam"));
		assertThat(customer.getLastName(), is("Dec"));
	}

	@Test
	public void savesNewClient() throws ParseException {
		// given
		Client stefan = new Client("Stefan", "Baranski", 1, new AuthData("login", "pass"));
		EmailAddress emailAddress = new EmailAddress("stefan.baranski@smoopay.com");
		stefan.setEmailAddress(emailAddress);
		ClientAddress address = new ClientAddress("a", "b", "22-333", "Poland", true);
		stefan.add(address);
		ClientAccount clientAccount = new ClientAccount("1", new AccountStatus(AccountStatusEnum.ACTIVE), new Currency(CurrencyEnum.PLN));
		Date expiryDate = new SimpleDateFormat("YYYY-MM-DD").parse("2013-09-09");
		clientAccount.setExpiryDate(expiryDate);
		ClientAccountData clientAccData = new ClientAccountData("ccc", "aaa", "bbb");
		clientAccount.setClientAccountData(clientAccData);
		ClientFinancialBalance financialBalance = new ClientFinancialBalance(new BigDecimal("100.0"));
		clientAccount.setClientFinancialBalance(financialBalance);
		stefan.add(clientAccount);

		// when
		Client result = clientRepository.save(stefan);

		// than
		assertThat(result, is(notNullValue()));
		assertThat(result.getId(), is(notNullValue()));
		assertThat(result.getFirstName(), is("Stefan"));
		assertThat(result.getLastName(), is("Baranski"));
		assertThat(result.getPesel(), is(1L));
		assertThat(result.getEmailAddress().getValue(), is("stefan.baranski@smoopay.com"));
		assertThat(((ClientAddress) result.getAddresses().toArray()[0]).isResident(), is(true));
		assertThat(((ClientAccount) result.getClientAccounts().toArray()[0]).getExpiryDate(), is(expiryDate));
		assertThat(((ClientAccount) result.getClientAccounts().toArray()[0]).getClientAccountData().getCashAccNRB(), is("aaa"));
	}

	@Test
	public void updateExistingClient() {
		// given
		Client adam = clientRepository.findOne(1L);
		adam.setEmailAddress(new EmailAddress("adam.dec2@smoopay.com"));

		// when
		clientRepository.save(adam);
		Client result = clientRepository.findOne(1L);

		// than
		assertThat(result, is(notNullValue()));
		assertThat(result.getId(), is(notNullValue()));
		assertThat(result.getFirstName(), is("Adam"));
		assertThat(result.getEmailAddress(), is(new EmailAddress("adam.dec2@smoopay.com")));
	}

	@Test
	public void findsClientByEmailAddress() {
		// when
		Client result = clientRepository.findByEmailAddress(new EmailAddress("adam.dec@smoopay.com"));

		// than
		assertThat(result, is(notNullValue()));
		assertThat(result.getFirstName(), is("Adam"));
		assertThat(result.getLastName(), is("Dec"));
	}

	@Test
	public void findsAllClients() {
		// when
		List<Client> customers = clientRepository.findAll();

		// than
		assertThat(customers, hasSize(3));
	}

	@Test
	public void findAllAddresses() {
		// given
		Iterable<ClientAddress> findAll = addressRepository.findAll();

		// than
		assertThat(findAll, is(notNullValue()));
	}

	@Test
	public void deletesClientAddress() {
		// given
		Client adam = clientRepository.findOne(1L);
		adam.removeAddresses();

		// when
		Client save = clientRepository.save(adam);
		ClientAddress findByStreet = addressRepository.findByStreet("M.C Sklodowskiej 5/6");

		// than
		assertThat(save.getAddresses().size(), is(0));
		assertThat(findByStreet, is(nullValue()));
	}

	@Test
	public void deletesClientsAccounts() {
		// given
		Client adam = clientRepository.findOne(1L);
		adam.removeClientAccounts();

		// when
		Client save = clientRepository.save(adam);
		ClientAccount findByAccountNumber = clientAccountRepository.findByVirtualAccNo("1");
		ClientAccountData findByCashAccNRB = accountDataRepository.findByCashAccNRB("12 1234 5678 1234 5678 9012 3456");
		ClientFinancialBalance findOne = financialBalanceRepository.findOne(1L);

		// than
		assertThat(save.getClientAccounts().size(), is(0));
		assertThat(findByAccountNumber, is(nullValue()));
		assertThat(findByCashAccNRB, is(nullValue()));
		assertThat(findOne, is(nullValue()));
	}

	@Test
	public void deletesClientByID() {
		// when
		clientRepository.delete(1L);

		// than
		assertThat(clientRepository.findOne(1L), is(nullValue()));
	}

	@Test
	public void accessesClientPageByPage() {
		// when
		Page<Client> result = clientRepository.findAll(new PageRequest(1, 1));

		// than
		assertThat(result, is(notNullValue()));
		assertThat(result.isFirstPage(), is(false));
		assertThat(result.isLastPage(), is(false));
		assertThat(result.getNumberOfElements(), is(1));
	}

	@Test
	public void countClientsUsingNativeQuery() {
		// given
		Session session = em.unwrap(Session.class);

		// when
		long count = ((Long) session.createQuery("select count(*) from Client").iterate().next()).intValue();

		// than
		assertThat(count, is(3L));
	}

	@Test
	public void changeAdamsBalanceUsingJPA() {
		// given
		QClient client = QClient.client;
		QClientAccount account = QClientAccount.clientAccount;
		BooleanExpression lastname = client.lastName.startsWith("Dec");
		BooleanExpression currency = account.currency.eq(new Currency(CurrencyEnum.PLN));
		JPAQuery query = new JPAQuery(em);

		// when
		List<Client> clients = query.from(client).join(client.clientAccounts, account).fetch().where(lastname, currency).list(client);
		ClientAccount clientAccount = (ClientAccount) clients.get(0).getClientAccounts().toArray()[0];
		clientAccount.getClientFinancialBalance().setPrimaryAccountBalance(
				clientAccount.getClientFinancialBalance().getPrimaryAccountBalance().add(new BigDecimal("100.0")));
		clientAccountRepository.save(clientAccount);

		// than
		Client result = clientRepository.findOne(1L);
		assertThat(((ClientAccount) result.getClientAccounts().toArray()[0]).getClientFinancialBalance().getPrimaryAccountBalance().doubleValue(), is(200.0D));
	}

	@Test
	public void changeAdamsBalanceUsingHibernate() {
		// given
		QClient client = QClient.client;
		QClientAccount account = QClientAccount.clientAccount;
		BooleanExpression lastname = client.lastName.startsWith("Dec");
		BooleanExpression currency = account.currency.eq(new Currency(CurrencyEnum.PLN));
		Session session = em.unwrap(Session.class);
		HibernateQuery query = new HibernateQuery(session);

		// when
		Client clients = query.from(client).join(client.clientAccounts, account).fetch().where(lastname, currency).singleResult(client);
		ClientAccount clientAccount = (ClientAccount) clients.getClientAccounts().toArray()[0];
		clientAccount.getClientFinancialBalance().setPrimaryAccountBalance(
				clientAccount.getClientFinancialBalance().getPrimaryAccountBalance().add(new BigDecimal("100.0")));
		clientAccountRepository.save(clientAccount);

		// than
		Client result = clientRepository.findOne(1L);
		assertThat(((ClientAccount) result.getClientAccounts().toArray()[0]).getClientFinancialBalance().getPrimaryAccountBalance().doubleValue(), is(200.0D));
	}

	@Test
	public void changeAdamsBalanceUsingCriteriaJPA() {
		// given
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Client> query = cb.createQuery(Client.class);
		Metamodel m = em.getMetamodel();
		EntityType<Client> client_ = m.entity(Client.class);
		Root<Client> root = query.from(Client.class);

		// when
		Join<Client, ClientAccount> accounts = root.join(client_.getSet("clientAccounts").getName());
		Predicate currencyP = cb.equal(accounts.get("currency"), new Currency(CurrencyEnum.PLN));
		query.select(root).where(currencyP).distinct(true);
		List<Client> resultList = em.createQuery(query).getResultList();
		ClientAccount clientAccount = (ClientAccount) resultList.get(0).getClientAccounts().toArray()[1];
		clientAccount.getClientFinancialBalance().setPrimaryAccountBalance(
				clientAccount.getClientFinancialBalance().getPrimaryAccountBalance().add(new BigDecimal("100.0")));
		clientAccountRepository.save(clientAccount);

		// than
		Client result = clientRepository.findOne(1L);
		assertThat(((ClientAccount) result.getClientAccounts().toArray()[1]).getClientFinancialBalance().getPrimaryAccountBalance().doubleValue(), is(200.0D));
	}

	@Test
	public void changeAdamsBalanceUsingCriteriaHibernate() {
		// given
		Session session = em.unwrap(Session.class);

		// when
		List<?> resultList = session.createCriteria(Client.class).createCriteria("clientAccounts")
				.add(Restrictions.like("currency", new Currency(CurrencyEnum.CHF))).list();
		ClientAccount clientAccount = (ClientAccount) ((Client) resultList.get(0)).getClientAccounts().toArray()[1];
		clientAccount.getClientFinancialBalance().setPrimaryAccountBalance(
				clientAccount.getClientFinancialBalance().getPrimaryAccountBalance().add(new BigDecimal("100.0")));
		clientAccountRepository.save(clientAccount);

		// than
		Client result = clientRepository.findOne(1L);
		assertThat(((ClientAccount) result.getClientAccounts().toArray()[1]).getClientFinancialBalance().getPrimaryAccountBalance().doubleValue(), is(200.0D));
	}

	/**
	 * Dynamic association fetching
	 */
	@Test
	public void listClientsAccountsUsingQueryCriteriaHibernate() {
		// given
		Session session = em.unwrap(Session.class);

		// when
		List<?> resultList = session.createCriteria(Client.class).setFetchMode("clientAccounts", FetchMode.SELECT).setFetchMode("addresses", FetchMode.SELECT)
				.list();

		// than
		assertThat(resultList.size(), is(3));
	}

	/**
	 * One query.
	 */
	@Test
	public void selectAdamsBalanceUsingJPQLNamedParams() {
		// given
		String jpql = "SELECT c FROM Client c JOIN FETCH c.clientAccounts acc JOIN FETCH c.addresses add JOIN FETCH acc.clientFinancialBalance fb JOIN FETCH acc.accountStatus nrb WHERE acc.currency = :currency AND add.city = :city";
		Query query = em.createQuery(jpql);
		query.setParameter("currency", new Currency(CurrencyEnum.PLN));
		query.setParameter("city", "Lezajsk");

		// when
		List<?> resultList = query.getResultList();

		// than
		assertThat(resultList.size(), is(1));
	}

	@Test
	public void selectAdamsBalanceUsingHQLNamedParams() {
		// given
		Session session = em.unwrap(Session.class);
		String hql = "SELECT c FROM Client c JOIN FETCH c.clientAccounts acc JOIN FETCH c.addresses add WHERE acc.currency = :currency AND add.city = :city";
		org.hibernate.Query query = session.createQuery(hql);
		query.setParameter("currency", new Currency(CurrencyEnum.CHF));
		query.setParameter("city", "Lezajsk");

		// when
		List<?> resultList = query.list();

		// than
		assertThat(resultList.size(), is(1));
	}

	@Test
	public void executesQuerydslPredicateAgainstClients() {
		// given
		QClient client = QClient.client;
		BooleanExpression firstnameStartsWithAd = client.firstName.startsWith("Ad");
		BooleanExpression lastnameContainsE = client.lastName.contains("e");

		// when
		Iterable<Client> result = clientRepository.findAll(firstnameStartsWithAd.or(lastnameContainsE));

		// than
		assertThat(result, is(Matchers.<Client> iterableWithSize(2)));
	}
}