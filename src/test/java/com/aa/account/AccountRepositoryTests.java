package com.aa.account;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@Rollback(false)
public class AccountRepositoryTests {

	@Autowired
	private AccountRepository repo;
	
	@Test
	public void testCreateAccount() {
		Account account = new Account("14616432643214564312", 5000);
		
		Account savedAccount = repo.save(account);
		
		assertThat(savedAccount).isNotNull();
		assertThat(savedAccount.getId()).isGreaterThan(0);
	}
}
