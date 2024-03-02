package com.vishal.transactions.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vishal.transactions.dto.TransactionRequest;
import com.vishal.transactions.dto.TransactionRespone;
import com.vishal.transactions.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	private final Logger logger = LoggerFactory.getLogger(TransactionController.class);

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping
	public ResponseEntity<TransactionRespone> createTransaction(@RequestBody TransactionRequest transactionRequest) {
		logger.debug("Request to create transaction : {} ", transactionRequest);

		TransactionRespone respone = transactionService.createTransaction(transactionRequest);
		return new ResponseEntity<>(respone, HttpStatus.CREATED);
	}

}