package com.conversor.currency.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conversor.currency.entity.User;
import com.conversor.currency.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Optional<User> validateUser(Long id) {

		Optional<User> user = userRepository.findById(id);

		return user;
	}

}
