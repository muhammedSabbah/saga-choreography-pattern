package com.woody.saga.choreography.pattern.event;

import java.util.Date;
import java.util.UUID;

import com.woody.saga.choreography.pattern.dto.OrderRequestDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderEvent implements Event {
	
	private UUID eventId=UUID.randomUUID();
    private Date eventDate=new Date();
	private OrderRequestDTO orderRequestDto;
	private OrderStatus orderStatus;
	
	public OrderEvent(OrderRequestDTO orderRequestDto, OrderStatus orderStatus) {
		this.orderRequestDto = orderRequestDto;
		this.orderStatus = orderStatus;
	}

	@Override
	public UUID getEventId() {
		return this.eventId;
	}

	@Override
	public Date getEventDate() {
		return this.eventDate;
	}
	
	
}
