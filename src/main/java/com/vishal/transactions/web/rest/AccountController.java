package com.vishal.transactions.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vishal.transactions.domain.Account;
import com.vishal.transactions.dto.AccountRequest;
import com.vishal.transactions.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private final Logger logger = LoggerFactory.getLogger(AccountController.class);

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping
	public ResponseEntity<Account> createAccount(@RequestBody AccountRequest accountRequest) {
		logger.debug("Incoming request for create account : {} ", accountRequest);

		Account account = accountService.createAccount(accountRequest);
		return new ResponseEntity<>(account, HttpStatus.CREATED);
	}

	@GetMapping("/{accountId}")
	public ResponseEntity<Account> getAccount(@PathVariable Long accountId) {
		logger.debug("Incoming request for get account by Id : {} ", accountId);

		Account account = accountService.getAccount(accountId);
		return ResponseEntity.ok(account);
	}
}