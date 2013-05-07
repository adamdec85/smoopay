package com.smoopay.sts.repository.common;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import com.smoopay.sts.entity.common.address.ClientAddress;

/**
 * {@link Repository} to access {@link ClientAddress} instances.
 * 
 * @author Adam Dec
 */
public interface AddressRepository extends CrudRepository<ClientAddress, Long>, QueryDslPredicateExecutor<ClientAddress> {

	/**
	 * Returns the Address.
	 * 
	 * @param street
	 *            the String to search for.
	 * @return Address
	 */
	ClientAddress findByStreet(String street);
}