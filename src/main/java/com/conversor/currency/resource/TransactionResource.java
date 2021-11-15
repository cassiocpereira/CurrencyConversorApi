package com.conversor.currency.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conversor.currency.entity.Transaction;
import com.conversor.currency.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping("/convert")
public class TransactionResource {

	private static Logger logger = LoggerFactory.getLogger(TransactionResource.class);

	private final String urlService = "https://v6.exchangerate-api.com/v6/ca89225a1f06850a744c01a6/latest/";

	@Autowired
	private TransactionRepository transactionRepository;

	@PostMapping
	public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction)
			throws JsonMappingException, JsonProcessingException, MalformedURLException, IOException {
		Double exchangeRate = findExchangeRate(transaction.getDestinationCurrency(), transaction.getOriginCurrency());
		transaction.setExchangeRate(exchangeRate);
		transaction.setDestinationValue(transaction.getExchangeRate() * transaction.getOriginValue());
		Instant now = Instant.now();
		transaction.setTransactionDate(now);

		transactionRepository.save(transaction);
		return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
	}

	private Double findExchangeRate(String destinationCurrency, String originCurrency)
			throws MalformedURLException, IOException, JsonProcessingException, JsonMappingException {
		logger.info("Finding exchange rate for operation " + originCurrency + ">>>" + destinationCurrency);
		URL url = new URL(urlService + "/" + destinationCurrency);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();
		JsonParser jp = new JsonParser();
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
		JsonObject jsonobj = root.getAsJsonObject();

		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {
		};

		Map<String, Object> malAll = new ObjectMapper().readValue(jsonobj.toString(), typeRef);
		Map<String, Object> mapConversionRates = (Map<String, Object>) malAll.get("conversion_rates");
		Double result = Double.parseDouble(mapConversionRates.get(originCurrency).toString());

		logger.info("Operation sucefully. Exchange Rate: " + result);
		return result;

	}

}
