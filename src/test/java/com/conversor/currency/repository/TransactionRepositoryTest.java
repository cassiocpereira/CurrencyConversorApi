package com.conversor.currency.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.conversor.currency.entity.Transaction;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransactionRepositoryTest {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Test
	public void testFindByUserIdReturnEmpty() {
		Long id = 1L;
		List<Transaction> listTransactions = transactionRepository.findByUserId(id);
		Assert.assertTrue(listTransactions.isEmpty());
	}

}
