package com.woody.saga.choreography.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woody.saga.choreography.pattern.dto.OrderRequestDTO;
import com.woody.saga.choreography.pattern.dto.PaymentRequestDTO;
import com.woody.saga.choreography.pattern.event.OrderEvent;
import com.woody.saga.choreography.pattern.event.PaymentEvent;
import com.woody.saga.choreography.pattern.event.PaymentStatus;
import com.woody.saga.choreography.payment.entity.UserTransaction;
import com.woody.saga.choreography.payment.repository.UserRepository;
import com.woody.saga.choreography.payment.repository.UserTransactionRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

	private UserRepository userRepository;
	private UserTransactionRepository userTransactionRepository;

	public UserRepository getUserRepository() {
		return userRepository;
	}

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserTransactionRepository getUserTransactionRepository() {
		return userTransactionRepository;
	}

	@Autowired
	public void setUserTransactionRepository(UserTransactionRepository userTransactionRepository) {
		this.userTransactionRepository = userTransactionRepository;
	}

	/*
	 * get the user ID check the balance availability if the balance sufficient,
	 * then Payment is completed and deduct amount from DB if not sufficient, then
	 * cancel the order
	 */

	@Override
	public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
		OrderRequestDTO orderRqDto = orderEvent.getOrderRequestDto();
		Integer userId = orderRqDto.getUserId();
		PaymentRequestDTO paymentRqDto = new PaymentRequestDTO(orderRqDto.getOrderId(), userId, orderRqDto.getAmount());
		return getUserRepository().findById(userId)
		.filter(user -> user.getCashMoney() >= orderRqDto.getAmount())
		.map(user -> {
			
			user.setCashMoney(user.getCashMoney() - orderRqDto.getAmount());
			UserTransaction userTransaction = new UserTransaction(orderRqDto.getOrderId(), userId,
					orderRqDto.getAmount());
			getUserTransactionRepository().save(userTransaction);
			return new PaymentEvent(paymentRqDto, PaymentStatus.PAYMENT_COMPLETED);
		}).orElse(new PaymentEvent(paymentRqDto, PaymentStatus.PAYMENT_FAILED));
	}

	@Override
	public PaymentEvent cancelOrderEvent(OrderEvent orderEvent) {
		// TODO Auto-generated method stub
		return null;
	}

}
