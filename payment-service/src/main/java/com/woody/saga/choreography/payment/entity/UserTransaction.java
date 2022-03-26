package com.woody.saga.choreography.payment.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_TRANSACTION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTransaction {

	@Id
	private Integer orderId;
	private Integer userId;
	private Integer amoumnt;
}
