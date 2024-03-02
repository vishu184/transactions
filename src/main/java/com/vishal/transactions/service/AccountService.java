package com.vishal.transactions.service;

import com.vishal.transactions.domain.Account;
import com.vishal.transactions.dto.AccountRequest;

public interface AccountService {
	
    Account createAccount(AccountRequest accountRequest);
    
    Account getAccount(Long accountId);
    
}