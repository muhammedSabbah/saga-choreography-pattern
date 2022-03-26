package com.woody.saga.choreography.order.config;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.woody.saga.choreography.order.entity.PurchaseOrder;
import com.woody.saga.choreography.order.repository.OrderRepository;
import com.woody.saga.choreography.order.service.OrderStatusPublisher;
import com.woody.saga.choreography.pattern.dto.OrderRequestDTO;
import com.woody.saga.choreography.pattern.event.OrderStatus;
import com.woody.saga.choreography.pattern.event.PaymentStatus;

@Configuration
public class OrderStatusHandler {

	private OrderRepository repository;
	private OrderStatusPublisher publisher;

	public OrderRepository getRepository() {
		return repository;
	}

	@Autowired
	public void setRepository(OrderRepository repository) {
		this.repository = repository;
	}

	public OrderStatusPublisher getPublisher() {
		return publisher;
	}

	@Autowired
	public void setPublisher(OrderStatusPublisher publisher) {
		this.publisher = publisher;
	}

	@Transactional
	public void updateOrder(int id, Consumer<PurchaseOrder> consumer) {
		getRepository().findById(id).ifPresent(consumer.andThen(this::updateOrder));
	}
	
	private void updateOrder(PurchaseOrder purchaseOrder) {
		Boolean isPaymentCompleted = PaymentStatus.PAYMENT_COMPLETED.equals(purchaseOrder.getPaymentStatus());
		OrderStatus orderStatus = isPaymentCompleted ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_FAILED;
		purchaseOrder.setOrderStatus(orderStatus);
		if (!isPaymentCompleted) {
			publisher.publishOrderEvent(mapEntityToOrderRequestDTO(purchaseOrder), orderStatus);
		}
	}
	
	private OrderRequestDTO mapEntityToOrderRequestDTO(PurchaseOrder purchaseOrder) {
		OrderRequestDTO orderRqDto = new OrderRequestDTO();
		orderRqDto.setUserId(purchaseOrder.getUserId());
		orderRqDto.setAmount(purchaseOrder.getPrice());
		orderRqDto.setProductId(purchaseOrder.getId());
		return orderRqDto;
	}
	
}
