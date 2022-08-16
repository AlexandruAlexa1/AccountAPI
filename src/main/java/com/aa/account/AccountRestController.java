package com.aa.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aa.account.exception.NotFoundException;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountRestController {

	@Autowired
	private AccountService service;
	
	@GetMapping
	public ResponseEntity<List<Account>> listAll() {
		List<Account> listAccounts = service.listAll();
		
		if (listAccounts.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return new ResponseEntity<>(listAccounts, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Account> getOne(@PathVariable("id") Integer id) throws NotFoundException {
		Account account = service.get(id);
		
		return new ResponseEntity<>(account, HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<Account> add(@RequestBody Account account) {
		Account savedAccount = service.save(account);
		
		return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
	}
}
