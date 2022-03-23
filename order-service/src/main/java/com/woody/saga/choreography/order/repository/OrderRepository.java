package com.woody.saga.choreography.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woody.saga.choreography.order.entity.PurchaseOrder;

@Repository
public interface OrderRepository extends JpaRepository<PurchaseOrder, Integer> {

}
