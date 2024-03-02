package com.vishal.transactions.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vishal.transactions.domain.Account;
import com.vishal.transactions.dto.AccountRequest;
import com.vishal.transactions.exception.ResourceNotFoundException;
import com.vishal.transactions.repository.AccountRepository;
import com.vishal.transactions.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Account createAccount(AccountRequest accountRequest) {
		logger.debug("Request to create Account with accountRequest : {} ", accountRequest);

		Account account = new Account();
		account.setDocumentNumber(accountRequest.getDocumentNumber());
		return accountRepository.save(account);
	}

	@Override
	public Account getAccount(Long accountId) {
		logger.debug("Request to get Account with Id : {} ", accountId);

		return accountRepository.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + accountId));
	}
}