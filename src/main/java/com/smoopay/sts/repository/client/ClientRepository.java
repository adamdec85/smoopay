package com.smoopay.sts.repository.client;

import java.util.List;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.smoopay.sts.entity.client.Client;
import com.smoopay.sts.entity.common.EmailAddress;

/**
 * {@link Repository} to access {@link Client} instances.
 * 
 * @author Adam Dec
 */
public interface ClientRepository extends PagingAndSortingRepository<Client, Long>, QueryDslPredicateExecutor<Client> {

	List<Client> findAll();

	@Transactional(timeout = 100)
	<S extends Client> S save(S entity);

	/**
	 * Returns the customer with the given {@link EmailAddress}.
	 * 
	 * @param emailAddress
	 *            the {@link EmailAddress} to search for.
	 * @return Client
	 */
	Client findByEmailAddress(EmailAddress emailAddress);

	/**
	 * Returns the customer with the given {@link firstName}.
	 * 
	 * @param firstName
	 *            the {@link String} to search for.
	 * @return Client
	 */
	List<Client> findByFirstName(String firstName);

	/**
	 * Returns the customer with the given {@link lastName}.
	 * 
	 * @param lastName
	 *            the {@link String} to search for.
	 * @return Client
	 */
	List<Client> findByLastName(String lastName);
}