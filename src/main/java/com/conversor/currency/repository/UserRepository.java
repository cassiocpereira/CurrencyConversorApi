package com.conversor.currency.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conversor.currency.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findById(Long id);
	
	boolean existsById(Long id);

}
