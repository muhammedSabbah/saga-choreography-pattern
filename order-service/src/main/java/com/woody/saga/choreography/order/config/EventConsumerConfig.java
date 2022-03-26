package com.woody.saga.choreography.order.config;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.woody.saga.choreography.pattern.event.PaymentEvent;

@Configuration
public class EventConsumerConfig {

	private OrderStatusHandler orderStatusHandler;

	public OrderStatusHandler getOrderStatusHandler() {
		return orderStatusHandler;
	}

	@Autowired
	public void setOrderStatusHandler(OrderStatusHandler orderStatusHandler) {
		this.orderStatusHandler = orderStatusHandler;
	}

	@Bean
	public Consumer<PaymentEvent> paymentEventConsumer() {
		return (payment) -> getOrderStatusHandler().updateOrder(payment.getPaymentRequestDTO().getOrderId(), po -> {
			po.setPaymentStatus(payment.getPaymentStatus());
		});
	}
}
