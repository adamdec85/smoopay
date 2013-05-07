package com.smoopay.sts.repository.common;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import com.smoopay.sts.entity.common.account.data.ClientAccountData;

/**
 * {@link Repository} to access {@link ClientAccountData} instances.
 * 
 * @author Adam Dec
 */
public interface AccountDataRepository extends CrudRepository<ClientAccountData, Long> {

	/**
	 * Returns the NRBAndIBAN.
	 * 
	 * @param cashAccNRB
	 *            the String to search for.
	 * @return NRBAndIBAN
	 */
	ClientAccountData findByCashAccNRB(String cashAccNRB);

	/**
	 * Returns the NRBAndIBAN.
	 * 
	 * @param cashAccIBAN
	 *            the String to search for.
	 * @return NRBAndIBAN
	 */
	ClientAccountData findBycashAccIBAN(String cashAccIBAN);
}