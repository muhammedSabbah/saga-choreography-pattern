package com.woody.saga.choreography.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woody.saga.choreography.pattern.dto.OrderRequestDTO;
import com.woody.saga.choreography.pattern.event.OrderEvent;
import com.woody.saga.choreography.pattern.event.OrderStatus;

import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;

@Service
public class OrderStatusPublisher {

	private Sinks.Many<OrderEvent> orderSinks;

	public Sinks.Many<OrderEvent> getOrderSinks() {
		return orderSinks;
	}

	@Autowired
	public void setOrderSinks(Sinks.Many<OrderEvent> orderSinks) {
		this.orderSinks = orderSinks;
	}

	public void publishOrderEvent(OrderRequestDTO orderRequestDTO, OrderStatus orderStatus) {

		OrderEvent orderEvent = new OrderEvent(orderRequestDTO, orderStatus);
		EmitResult emitResult = getOrderSinks().tryEmitNext(orderEvent);
		System.out.println(getClass().getCanonicalName() + ", Publish Order Evenet , emitResult IS SUCCESS : "
				+ emitResult.isSuccess());

	}
}
