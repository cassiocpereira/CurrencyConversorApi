package com.conversor.currency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conversor.currency.dto.UserDto;
import com.conversor.currency.entity.User;
import com.conversor.currency.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User createUser(UserDto userDto) {

		User user = new User();
		user.setName(userDto.getName());
		userRepository.save(user);

		return user;

	}
}
