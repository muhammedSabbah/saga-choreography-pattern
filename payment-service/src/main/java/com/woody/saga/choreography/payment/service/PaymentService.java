package com.woody.saga.choreography.payment.service;

import com.woody.saga.choreography.pattern.event.OrderEvent;
import com.woody.saga.choreography.pattern.event.PaymentEvent;

public interface PaymentService {

	PaymentEvent newOrderEvent(OrderEvent orderEvent);

	PaymentEvent cancelOrderEvent(OrderEvent orderEvent);

}
