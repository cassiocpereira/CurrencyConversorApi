package com.conversor.currency.resource;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionResourceTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testCreateTransactionReturn201() throws Exception {
		URI uri = new URI("/api/convert");
		String json = "{\r\n"
				+ "    \"originCurrency\": \"BRL\",\r\n"
				+ "    \"destinationCurrency\":\"USD\",\r\n"
				+ "    \"originValue\": 100.0,\r\n"
				+ "    \"userId\": 1\r\n"
				+ "}";
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(201));
	}
	
	@Test
	public void testCreateTransactionWithoutOriginCurrencyReturn400() throws Exception {
		URI uri = new URI("/api/convert");
		String json = "{\r\n"
				+ "    \"originCurrency\": \"\",\r\n"
				+ "    \"destinationCurrency\":\"USD\",\r\n"
				+ "    \"originValue\": 100.0,\r\n"
				+ "    \"userId\": 1\r\n"
				+ "}";
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(400));
	}
	
	@Test
	public void testCreateTransactionWithNotExistentUserReturn404() throws Exception {
		URI uri = new URI("/api/convert");
		String json = "{\r\n"
				+ "    \"originCurrency\": \"BRL\",\r\n"
				+ "    \"destinationCurrency\":\"USD\",\r\n"
				+ "    \"originValue\": 100.0,\r\n"
				+ "    \"userId\": 99\r\n"
				+ "}";
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(404));
	}
	
	@Test
	public void testCreateTransactionWithInexistentCurrencyReturn404() throws Exception {
		URI uri = new URI("/api/convert");
		String json = "{\r\n"
				+ "    \"originCurrency\": \"BRL\",\r\n"
				+ "    \"destinationCurrency\":\"XXX\",\r\n"
				+ "    \"originValue\": 100.0,\r\n"
				+ "    \"userId\": 1\r\n"
				+ "}";
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(404));
	}
	
	@Test
	public void testTransactionsByNotExistentUserReturn404() throws Exception {
		URI uri = new URI("api/user/transactions/99");
		mockMvc.perform(MockMvcRequestBuilders
				.get(uri).contentType(MediaType.TEXT_HTML))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(404));
	}

}
