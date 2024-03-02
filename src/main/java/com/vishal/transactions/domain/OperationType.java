package com.vishal.transactions.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "operation_types")
@Data
public class OperationType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "operation_type_id")
	private Long id;

	private String description;

	@Column(name = "amount_sign")
	private Integer amountSign;

}
