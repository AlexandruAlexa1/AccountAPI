package com.aa.account;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DBLoader {

	@Autowired
	private AccountRepository repo;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Bean
	public CommandLineRunner initDB() {
		return args -> {
			Account account1 = new Account("14616432643214564312", 5000);
			Account account2 = new Account("24616432643214564312", 1000);
			Account account3 = new Account("84616432643214564312", 100);
			Account account4 = new Account("94616432643214564312", 50);
			Account account5 = new Account("34616432643214564312", 30);

			repo.saveAll(List.of(account1, account2, account3, account4, account5));
			
			logger.info("AA Sample database initialized");
		};
	}
}
