package com.woody.saga.choreography.order.service;

import java.util.List;

import com.woody.saga.choreography.order.entity.PurchaseOrder;
import com.woody.saga.choreography.pattern.dto.OrderRequestDTO;

public interface OrderService {

	PurchaseOrder createOrder(OrderRequestDTO requestDTO);
	
	List<PurchaseOrder> findAll();

}
