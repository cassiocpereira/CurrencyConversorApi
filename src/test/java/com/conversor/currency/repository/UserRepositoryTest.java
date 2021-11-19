package com.conversor.currency.repository;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.conversor.currency.entity.User;
import com.conversor.currency.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testFindByIdExistentUser() {
		Long id = 1L;
		String name = "CASSIO";
		Optional<User> user = userRepository.findById(id);
		Assert.assertEquals(id, user.get().getId());
		Assert.assertEquals(name, user.get().getName());
	}

	@Test
	public void testExistsByIdExistentUser() {
		Long id = 1L;
		boolean user = userRepository.existsById(id);
		Assert.assertTrue(user);
	}

	@Test
	public void testExistsByIdNotExistentUser() {
		Long id = 99L;
		boolean user = userRepository.existsById(id);
		Assert.assertFalse(user);
	}
}
