package com.aa.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountRestController {

	@Autowired
	private AccountService service;
	
	@GetMapping
	public List<Account> listAll() {
		return service.listAll();
	}
}
