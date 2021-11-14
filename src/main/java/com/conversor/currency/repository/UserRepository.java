package com.conversor.currency.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conversor.currency.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
