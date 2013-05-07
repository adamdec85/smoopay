package com.smoopay.sts.repository.merchant;

import java.util.List;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import com.smoopay.sts.entity.common.account.MerchantAccount;

/**
 * {@link Repository} to access {@link MerchantAccount} instances.
 * 
 * @author Adam Dec
 */
public interface MerchantAccountRepository extends CrudRepository<MerchantAccount, Long>, QueryDslPredicateExecutor<MerchantAccount> {

	/**
	 * Returns the MerchantAccount.
	 * 
	 * @param virtualAccNo
	 *            the String to search for.
	 * @return Merchant
	 */
	MerchantAccount findByVirtualAccNo(String virtualAccNo);

	/**
	 * Returns all MerchantAccount.
	 * 
	 * @return List of MerchantAccount
	 */
	List<MerchantAccount> findAll();
}