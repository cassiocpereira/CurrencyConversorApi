package com.conversor.currency.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conversor.currency.dto.UserDto;
import com.conversor.currency.entity.User;
import com.conversor.currency.repository.UserRepository;
import com.conversor.currency.service.UserService;

@RestController
@RequestMapping("/v1")
public class UserResource {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/user/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createUser(@RequestBody @Valid UserDto userDto) {
		
		User user = userService.createUser(userDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

}
