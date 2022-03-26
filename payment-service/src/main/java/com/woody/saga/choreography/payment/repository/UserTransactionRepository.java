package com.woody.saga.choreography.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woody.saga.choreography.payment.entity.UserTransaction;

@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransaction, Integer> {

}
