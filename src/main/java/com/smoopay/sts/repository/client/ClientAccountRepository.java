package com.smoopay.sts.repository.client;

import java.util.List;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import com.smoopay.sts.entity.common.Currency;
import com.smoopay.sts.entity.common.account.ClientAccount;
import com.smoopay.sts.entity.common.account.data.ClientAccountData;
import com.smoopay.sts.entity.common.account.status.AccountStatus;

/**
 * {@link Repository} to access {@link ClientAccount} instances.
 * 
 * @author Adam Dec
 */
public interface ClientAccountRepository extends CrudRepository<ClientAccount, Long>, QueryDslPredicateExecutor<ClientAccount> {

	/**
	 * Returns the ClientAccount.
	 * 
	 * @param virtualAccNo
	 *            the String to search for.
	 * @return ClientAccount
	 */
	ClientAccount findByVirtualAccNo(String virtualAccNo);

	/**
	 * Returns the ClientAccount.
	 * 
	 * @param currency
	 *            the Currency to search for.
	 * @return List of ClientAccount
	 */
	List<ClientAccount> findByCurrency(Currency currency);

	/**
	 * Returns the ClientAccount.
	 * 
	 * @param clientAccountData
	 *            the ClientAccountData to search for.
	 * @return ClientAccount
	 */
	ClientAccount findByClientAccountData(ClientAccountData clientAccountData);

	/**
	 * Returns the ClientAccount.
	 * 
	 * @param accountStatus
	 *            the AccountStatus to search for.
	 * @return List of ClientAccount
	 */
	List<ClientAccount> findByAccountStatus(AccountStatus accountStatus);
}