package com.vishal.transactions.service;

import com.vishal.transactions.dto.TransactionRequest;
import com.vishal.transactions.dto.TransactionRespone;

public interface TransactionService {

	TransactionRespone createTransaction(TransactionRequest transactionRequest);

}