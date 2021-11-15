package com.conversor.currency.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conversor.currency.entity.User;
import com.conversor.currency.repository.UserRepository;

@RestController
@RequestMapping("/currencyConversor")
public class UserResource {

	@Autowired
	private UserRepository userRepository;

	@PostMapping(value = "/createUser")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		userRepository.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

}
