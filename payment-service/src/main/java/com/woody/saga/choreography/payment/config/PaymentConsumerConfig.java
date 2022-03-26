package com.woody.saga.choreography.payment.config;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.woody.saga.choreography.pattern.event.OrderEvent;
import com.woody.saga.choreography.pattern.event.OrderStatus;
import com.woody.saga.choreography.pattern.event.PaymentEvent;
import com.woody.saga.choreography.payment.service.PaymentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class PaymentConsumerConfig {

	private PaymentService paymentService;

	public PaymentService getPaymentService() {
		return paymentService;
	}

	@Autowired
	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@Bean
	public Function<Flux<OrderEvent>, Flux<PaymentEvent>> paymentProcessor() {
		return orderEventFlux -> orderEventFlux.flatMap(this::processPayment);
	}

	private Mono<PaymentEvent> processPayment(OrderEvent orderEvent) {
		if (OrderStatus.ORDER_CREATED.equals(orderEvent.getOrderStatus())) {
			return Mono.fromSupplier(() -> this.getPaymentService().newOrderEvent(orderEvent));
		}
		return Mono.fromSupplier(() -> this.getPaymentService().cancelOrderEvent(orderEvent));
	}
}
