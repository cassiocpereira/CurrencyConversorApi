package com.conversor.currency.resource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.tomcat.util.http.parser.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.conversor.currency.dto.TransactionDto;
import com.conversor.currency.entity.Transaction;
import com.conversor.currency.entity.User;
import com.conversor.currency.repository.TransactionRepository;
import com.conversor.currency.repository.UserRepository;
import com.conversor.currency.service.TransactionService;
import com.conversor.currency.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/api")
public class TransactionResource {

	private static Logger logger = LoggerFactory.getLogger(TransactionResource.class);

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private UserRepository userRepository;

	@PostMapping(value = "/convert")
	public ResponseEntity<?> createTransaction(@RequestBody @Valid TransactionDto transactionDto)
			throws JsonMappingException, JsonProcessingException, MalformedURLException, IOException {
		try {
			
			logger.info("Start searching for user id " + transactionDto.getUserId());
			
			if (userRepository.existsById(transactionDto.getUserId())) {	
				logger.info("Validating currencies " + transactionDto.getOriginCurrency() + "/"
						+ transactionDto.getDestinationCurrency());
				
				if (transactionService.validateSupportedCurrency(transactionDto.getOriginCurrency(),
						transactionDto.getDestinationCurrency())) {
					
					logger.info("Searching exchange rate in external service and converting value...");
					
					Transaction transaction = transactionService.convertCurrency(transactionDto);
					
					logger.info("Conversion successful: " + transaction.toString());

					return ResponseEntity.status(HttpStatus.CREATED).body(transaction);

				} else {
					logger.error("Informated currencies are not supported");
					return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
				}

			} else {
				logger.error("User not find for this operation");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

		} catch (Exception e) {
			logger.error(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GetMapping(value = "/user/transactions/{id}")
	public ResponseEntity<List<Transaction>> transactionsByUser(@PathVariable Long id) {
		logger.info(null);
		List<Transaction> listTrans = transactionRepository.findByUserId(id);
		return ResponseEntity.status(HttpStatus.OK).body(listTrans);
	}

}
