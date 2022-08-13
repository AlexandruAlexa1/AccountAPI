package com.aa.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repo;
	
	public List<Account> listAll() {
		return repo.findAll();
	}
	
	public Account get(Integer id) {
		return repo.findById(id).get();
	}
	
	public Account save(Account account) {
		return repo.save(account);
	}
	
	public Account deposit(float amount, Integer id) {
		repo.updateBalance(amount, id);
		
		return repo.findById(id).get();
	}
	
	public Account withdraw(float amount, Integer id) {
		repo.updateBalance(-amount, id);
		
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
