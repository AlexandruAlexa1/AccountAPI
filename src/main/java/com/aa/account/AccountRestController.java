package com.aa.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aa.account.exception.NotFoundException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountRestController {

	@Autowired
	private AccountService service;
	
	@GetMapping
	public ResponseEntity<CollectionModel<Account>> listAll() throws NotFoundException {
		List<Account> listAccounts = service.listAll();
		
		for (Account account : listAccounts) {
			account.add(linkTo(methodOn(AccountRestController.class).getOne(account.getId())).withSelfRel());
			account.add(linkTo(methodOn(AccountRestController.class).listAll()).withRel(IanaLinkRelations.COLLECTION));
		}
		
		if (listAccounts.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		CollectionModel<Account> model = CollectionModel.of(listAccounts);
		model.add(linkTo(methodOn(AccountRestController.class).listAll()).withSelfRel());
		
		return new ResponseEntity<>(model, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Account> getOne(@PathVariable("id") Integer id) throws NotFoundException {
		Account account = service.get(id);
		
		account.add(linkTo(methodOn(AccountRestController.class).getOne(account.getId())).withSelfRel());
		account.add(linkTo(methodOn(AccountRestController.class).listAll()).withRel(IanaLinkRelations.COLLECTION));
		
		return new ResponseEntity<>(account, HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<Account> add(@RequestBody Account account) {
		Account savedAccount = service.save(account);
		
		return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
	}
}
