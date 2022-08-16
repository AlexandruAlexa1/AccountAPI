package com.aa.account;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aa.account.exception.NotFoundException;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repo;
	
	public List<Account> listAll() {
		return repo.findAll();
	}
	
	public Account get(Integer id) throws NotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new NotFoundException("Could not find any Account with ID: " + id);
		}
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
