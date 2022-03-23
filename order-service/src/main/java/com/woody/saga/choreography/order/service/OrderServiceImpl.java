package com.woody.saga.choreography.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woody.saga.choreography.order.entity.PurchaseOrder;
import com.woody.saga.choreography.order.repository.OrderRepository;
import com.woody.saga.choreography.pattern.dto.OrderRequestDTO;
import com.woody.saga.choreography.pattern.event.OrderStatus;

@Service
public class OrderServiceImpl implements OrderService {

	private OrderRepository orderRepository;

	private OrderStatusPublisher orderPublisher;

	public OrderRepository getOrderRepository() {
		return orderRepository;
	}

	@Autowired
	public void setOrderRepository(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public OrderStatusPublisher getOrderPublisher() {
		return orderPublisher;
	}

	@Autowired
	public void setOrderPublisher(OrderStatusPublisher orderPublisher) {
		this.orderPublisher = orderPublisher;
	}

	@Override
	@Transactional
	public PurchaseOrder createOrder(OrderRequestDTO requestDTO) {
		PurchaseOrder purchaseOrder = getOrderRepository().save(mapOrderRequestDTOToPurchaseOrder(requestDTO));
		requestDTO.setOrderId(purchaseOrder.getId());
		// TODO
		// produce kafka event with status ORDER_CREATED
		getOrderPublisher().publishOrderEvent(requestDTO, OrderStatus.ORDER_CREATED);
		return purchaseOrder;
	}

	private PurchaseOrder mapOrderRequestDTOToPurchaseOrder(OrderRequestDTO requestDTO) {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setProductId(requestDTO.getProductId());
		purchaseOrder.setUserId(requestDTO.getUserId());
		purchaseOrder.setPrice(requestDTO.getAmount());
		purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
		return null;
	}

	@Override
	public List<PurchaseOrder> findAll() {
		return getOrderRepository().findAll();
	}

}
