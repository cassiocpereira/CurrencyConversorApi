package com.conversor.currency.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.conversor.currency.enums.CurrencyEnum;
import com.conversor.currency.enums.ErrorMessageEnum;
import com.conversor.currency.dto.TransactionDto;
import com.conversor.currency.entity.Transaction;
import com.conversor.currency.entity.User;
import com.conversor.currency.repository.TransactionRepository;
import com.conversor.currency.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserRepository userRepository;

	@Value("${url.external.service}")
	private String urlService;

	public Transaction convertCurrency(TransactionDto dto)
			throws JsonMappingException, JsonProcessingException, MalformedURLException, IOException {
		try {
			Double exchangeRate = findExchangeRate(dto.getDestinationCurrency(), dto.getOriginCurrency());

			Double destinationValue = (double) Math.round(exchangeRate * dto.getOriginValue());

			Optional<User> user = Optional.of(new User());
			user = userRepository.findById(dto.getUserId());

			Transaction transaction = new Transaction();
			transaction.setOriginCurrency(dto.getOriginCurrency());
			transaction.setDestinationCurrency(dto.getDestinationCurrency());
			transaction.setUser(user.get());
			transaction.setExchangeRate(exchangeRate);
			transaction.setDestinationValue(destinationValue);
			transaction.setOriginValue(dto.getOriginValue());
			transaction.setTransactionDate(Instant.now());

			transactionRepository.save(transaction);

			return transaction;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	private Double findExchangeRate(String destinationCurrency, String originCurrency)
			throws MalformedURLException, IOException, JsonProcessingException, JsonMappingException {
		try {
			URL url = new URL(urlService + destinationCurrency);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			request.connect();
			JsonParser jp = new JsonParser();
			JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
			JsonObject jsonobj = root.getAsJsonObject();

			TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {
			};

			Map<String, Object> mapAll = new ObjectMapper().readValue(jsonobj.toString(), typeRef);
			Map<String, Object> mapConversionRates = (Map<String, Object>) mapAll.get("conversion_rates");
			Double result = Double.parseDouble(mapConversionRates.get(originCurrency).toString());

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public boolean validateSupportedCurrency(String originCurrency, String destinationCurrency) {

		HashSet<String> supportedCurrencies = new HashSet<String>();

		for (CurrencyEnum c : CurrencyEnum.values()) {
			supportedCurrencies.add(c.name());
		}

		if (supportedCurrencies.contains(originCurrency) && supportedCurrencies.contains(destinationCurrency)) {
			return true;
		} else {
			return false;
		}

	}
	
	public String getErrorMessage(ErrorMessageEnum errorMessageEnum){

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("error", errorMessageEnum.getErrorCode());
		jsonObject.addProperty("message", errorMessageEnum.getErrorMessage());

		return jsonObject.toString();
	}

}
