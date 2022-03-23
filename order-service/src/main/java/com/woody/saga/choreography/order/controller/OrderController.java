package com.woody.saga.choreography.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woody.saga.choreography.order.entity.PurchaseOrder;
import com.woody.saga.choreography.order.service.OrderService;
import com.woody.saga.choreography.pattern.dto.OrderRequestDTO;

@RestController
@RequestMapping("/order")
public class OrderController {

	private OrderService orderService;

	public OrderService getOrderService() {
		return orderService;
	}

	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/create")
	public PurchaseOrder createOrder(@RequestBody OrderRequestDTO requestDTO) {
		return getOrderService().createOrder(requestDTO);
	}
	
	@GetMapping()
	public List<PurchaseOrder> getOrders() {
		return getOrderService().findAll();
	}
}
