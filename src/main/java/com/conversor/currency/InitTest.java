package com.conversor.currency;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class InitTest {
	
	@GetMapping("/init")
	public String test() {
		return "Currency Conversor API";
	}
	
}
