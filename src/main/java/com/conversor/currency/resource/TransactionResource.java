package com.conversor.currency.resource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conversor.currency.dto.TransactionDto;
import com.conversor.currency.entity.Transaction;
import com.conversor.currency.entity.User;
import com.conversor.currency.repository.TransactionRepository;
import com.conversor.currency.service.TransactionService;
import com.conversor.currency.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping
public class TransactionResource {

	private static Logger logger = LoggerFactory.getLogger(TransactionResource.class);

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;

	@PostMapping(value = "/convert")
	public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDto transactionDto)
			throws JsonMappingException, JsonProcessingException, MalformedURLException, IOException {

		Optional<User> user = userService.validateUser(transactionDto.getUserId());

		Transaction transaction = transactionService.convertCurrency(transactionDto, user);

		return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
	}

	@GetMapping(value = "/transactions/{id}")
	public ResponseEntity<List<Transaction>> transactionsByUser(@PathVariable Long id) {
		List<Transaction> listTrans = transactionRepository.findByUserId(id);
		return ResponseEntity.ok(listTrans);
	}

}
