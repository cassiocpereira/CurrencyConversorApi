package com.conversor.currency.resource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conversor.currency.dto.TransactionDto;
import com.conversor.currency.entity.Transaction;
import com.conversor.currency.enums.ErrorMessageEnum;
import com.conversor.currency.repository.TransactionRepository;
import com.conversor.currency.repository.UserRepository;
import com.conversor.currency.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/v1")
public class TransactionResource {

	private static Logger logger = LoggerFactory.getLogger(TransactionResource.class);
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private UserRepository userRepository;

	@PostMapping(value = "/transaction/convert", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createTransaction(@RequestBody @Valid TransactionDto transactionDto)
			throws JsonMappingException, JsonProcessingException, MalformedURLException, IOException {
		try {
			
			logger.info("Start searching for user id " + transactionDto.getUser());
			
			if (userRepository.existsById(transactionDto.getUser())) {	
				logger.info("Validating currencies " + transactionDto.getOriginCurrency() + "/"
						+ transactionDto.getDestinationCurrency());
				
				if (transactionService.validateSupportedCurrency(transactionDto.getOriginCurrency(),
						transactionDto.getDestinationCurrency())) {
					
					logger.info("Searching exchange rate in external service and converting value...");
					
					Transaction transaction = transactionService.convertCurrency(transactionDto);
					
					logger.info("Conversion successful: " + transaction.toString());

					return ResponseEntity.status(HttpStatus.CREATED).body(transaction);

				} else {
					logger.error(ErrorMessageEnum.CURRENCY_NOT_SUPPORTED.getErrorMessage());
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(transactionService.getErrorMessage(ErrorMessageEnum.CURRENCY_NOT_SUPPORTED));
				}

			} else {
				logger.error(ErrorMessageEnum.USER_NOT_FOUND.getErrorMessage());
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(transactionService.getErrorMessage(ErrorMessageEnum.USER_NOT_FOUND));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(transactionService.getErrorMessage(ErrorMessageEnum.UNEXPECTED_ERROR));	
		}

	}

	@GetMapping(value = "/transaction/user/{id}")
	public ResponseEntity<List<Transaction>> transactionsByUser(@PathVariable Long id) {
		try {
			logger.info("Searching transactions for user " + id);
			List<Transaction> listTrans = transactionRepository.findByUserId(id);
			return ResponseEntity.status(HttpStatus.OK).body(listTrans);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	

}
