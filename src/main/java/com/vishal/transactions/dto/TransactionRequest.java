package com.vishal.transactions.dto;

import lombok.Data;

@Data
public class TransactionRequest {
	
	private Long accountId;
	private Long operationTypeId;
	private Double amount;
	
}
