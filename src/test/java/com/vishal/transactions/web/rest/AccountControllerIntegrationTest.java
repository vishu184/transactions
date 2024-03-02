package com.vishal.transactions.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.vishal.transactions.dto.AccountRequest;
import com.vishal.transactions.repository.AccountRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AccountRepository accountRepository;

	@Test
	public void testCreateAccount() throws Exception {
		AccountRequest accountRequest = new AccountRequest();
		accountRequest.setDocumentNumber("1111");

		mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(accountRequest))).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.document_number").value(accountRequest.getDocumentNumber()));
	}

	@Test
	public void testGetAccount() throws Exception {

		Account account = new Account();
		account.setDocumentNumber("1111");
		accountRepository.save(account);

		mockMvc.perform(get("/accounts/{id}", account.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(account.getId()))
				.andExpect(jsonPath("$.document_number").value(account.getDocumentNumber()));
	}

	@Test
	public void testGetAccountNotFound() throws Exception {

		mockMvc.perform(get("/accounts/{id}", 1234L)).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value("Account not found with id 1234"));
	}
}
