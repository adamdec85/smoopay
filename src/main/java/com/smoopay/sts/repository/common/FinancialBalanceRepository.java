package com.smoopay.sts.repository.common;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import com.smoopay.sts.entity.common.ClientFinancialBalance;

/**
 * {@link Repository} to access {@link ClientFinancialBalance} instances.
 * 
 * @author Adam Dec
 */
public interface FinancialBalanceRepository extends CrudRepository<ClientFinancialBalance, Long> {
}