package com.vishal.transactions.service.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vishal.transactions.domain.Account;
import com.vishal.transactions.domain.OperationType;
import com.vishal.transactions.domain.Transaction;
import com.vishal.transactions.dto.TransactionRequest;
import com.vishal.transactions.dto.TransactionRespone;
import com.vishal.transactions.exception.BadRequestException;
import com.vishal.transactions.repository.AccountRepository;
import com.vishal.transactions.repository.OperationTypeRepository;
import com.vishal.transactions.repository.TransactionRepository;
import com.vishal.transactions.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	private final TransactionRepository transactionRepository;

	private final AccountRepository accountRepository;

	private final OperationTypeRepository operationTypeRepository;

	public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository,
			OperationTypeRepository operationTypeRepository) {
		this.transactionRepository = transactionRepository;
		this.accountRepository = accountRepository;
		this.operationTypeRepository = operationTypeRepository;
	}

	@Override
	public TransactionRespone createTransaction(TransactionRequest transactionRequest) {
		logger.debug("Request to Create Transaction : {} ", transactionRequest);

		Account account = accountRepository.findById(transactionRequest.getAccountId()).orElseThrow(
				() -> new BadRequestException("Account not found with id " + transactionRequest.getAccountId()));

		OperationType operationType = operationTypeRepository.findById(transactionRequest.getOperationTypeId())
				.orElseThrow(() -> new BadRequestException(
						"OperationType not found with id " + transactionRequest.getOperationTypeId()));

		validateTransactionAmount(operationType, transactionRequest.getAmount());

		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setOperationType(operationType);
		transaction.setAmount(transactionRequest.getAmount());
		transaction.setEventDate(LocalDateTime.now());

		return new TransactionRespone(transactionRepository.save(transaction).getId());
	}

	private void validateTransactionAmount(OperationType operationType, Double amount) {
		logger.debug("Request to validate transactionAmount with opearationType : {} and amount : {}", operationType,
				amount);

		Integer sign = (amount >= 0) ? 1 : -1;

		if (operationType.getAmountSign() != sign) {
			throw new BadRequestException("Amount for this operation type is invalid");
		}
	}

}