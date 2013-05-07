package com.smoopay.sts.repository.merchant;

import java.util.List;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import com.smoopay.sts.entity.merchant.Merchant;

/**
 * {@link Repository} to access {@link MerchantRepository} instances.
 * 
 * @author Adam Dec
 */
public interface MerchantRepository extends CrudRepository<Merchant, Long>, QueryDslPredicateExecutor<Merchant> {

	/**
	 * Returns the Merchant.
	 * 
	 * @param name
	 *            the String to search for.
	 * @return Merchant
	 */
	Merchant findByName(String name);

	/**
	 * Returns all Merchants.
	 * 
	 * @return List of Merchant
	 */
	List<Merchant> findAll();
}