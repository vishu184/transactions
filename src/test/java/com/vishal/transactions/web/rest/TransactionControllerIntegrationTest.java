package com.vishal.transactions.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vishal.transactions.domain.Account;
import com.vishal.transactions.domain.OperationType;
import com.vishal.transactions.dto.TransactionRequest;
import com.vishal.transactions.repository.AccountRepository;
import com.vishal.transactions.repository.OperationTypeRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private OperationTypeRepository operationTypeRepository;

	@Test
	public void testCreateTransaction() throws Exception {
		Account account = new Account();
		account.setDocumentNumber("1111");
		accountRepository.save(account);

		OperationType operationType = new OperationType();
		operationType.setDescription("Normal Purchase");
		operationType.setAmountSign(-1);
		operationTypeRepository.save(operationType);

		Double amount = -1234.1234;

		TransactionRequest request = new TransactionRequest();
		request.setAccountId(account.getId());
		request.setAmount(amount);
		request.setOperationTypeId(operationType.getId());

		mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.transaction_id").exists());

	}

	@Test
	public void testCreateTransactionInvalidAmount() throws Exception {
		Account account = new Account();
		account.setDocumentNumber("1111");
		accountRepository.save(account);

		OperationType operationType = new OperationType();
		operationType.setDescription("Normal Purchase");
		operationType.setAmountSign(1);
		operationTypeRepository.save(operationType);

		Double amount = -1234.1234;

		TransactionRequest request = new TransactionRequest();
		request.setAccountId(account.getId());
		request.setAmount(amount);
		request.setOperationTypeId(operationType.getId());

		mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status_code").value(400))
				.andExpect(jsonPath("$.message").value("Amount for this operation type is invalid"));
	}

	@Test
	public void testCreateTransactionInvalidAccount() throws Exception {
		OperationType operationType = new OperationType();
		operationType.setDescription("Normal Purchase");
		operationType.setAmountSign(-1);
		operationTypeRepository.save(operationType);

		Double amount = -1234.1234;

		TransactionRequest request = new TransactionRequest();
		request.setAccountId(1234L);
		request.setAmount(amount);
		request.setOperationTypeId(operationType.getId());

		mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status_code").value(400))
				.andExpect(jsonPath("$.message").value("Account not found with id 1234"));
	}

	@Test
	public void testCreateTransactionInvalidOperationType() throws Exception {
		Account account = new Account();
		account.setDocumentNumber("1111");
		accountRepository.save(account);

		Double amount = -1234.1234;

		TransactionRequest request = new TransactionRequest();
		request.setAccountId(account.getId());
		request.setAmount(amount);
		request.setOperationTypeId(1234L);

		mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status_code").value(400))
				.andExpect(jsonPath("$.message").value("OperationType not found with id 1234"));
	}
}
