package com.aa.account;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aa.account.exception.NotFoundException;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountRestController {

	@Autowired
	private AccountService service;
	
	@Autowired
	private AccountModelAssember assembler;
	
	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Account>>> listAll() throws NotFoundException {
		List<Account> listAccounts = service.listAll();
		
		if (listAccounts.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		List<EntityModel<Account>> accounts = listAccounts.stream().map(assembler::toModel).collect(Collectors.toList());

		CollectionModel<EntityModel<Account>> collectionModel = CollectionModel.of(accounts);
		collectionModel.add(linkTo(methodOn(AccountRestController.class).listAll()).withSelfRel());
		
		return new ResponseEntity<>(collectionModel, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Account>> getOne(@PathVariable("id") Integer id) throws NotFoundException {
		Account account = service.get(id);
		EntityModel<Account> EntityModel = assembler.toModel(account);
		
		return new ResponseEntity<>(EntityModel, HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<EntityModel<Account>> add(@RequestBody Account account) throws NotFoundException {
		Account savedAccount = service.save(account);
		EntityModel<Account> EntityModel = assembler.toModel(savedAccount);
		
		return ResponseEntity.created(linkTo(methodOn(AccountRestController.class).getOne(savedAccount.getId())).toUri())
				.body(EntityModel);
	}
	
	@PutMapping
	public ResponseEntity<EntityModel<Account>> update(@RequestBody Account account) throws NotFoundException {
		Account updatedAccount = service.save(account);
		EntityModel<Account> EntityModel = assembler.toModel(updatedAccount);
		
		return new ResponseEntity<>(EntityModel, HttpStatus.OK);
	}
	
	@PatchMapping("/{id}/deposit")
	public ResponseEntity<EntityModel<Account>> deposit(@PathVariable("id") Integer id, @RequestBody Amount amount) throws NotFoundException {
		Account updatedAccount = service.deposit(amount.getAmount(), id);
		EntityModel<Account> EntityModel = assembler.toModel(updatedAccount);
		
		return new ResponseEntity<>(EntityModel, HttpStatus.OK);
	}
	
	@PatchMapping("/{id}/withdraw")
	public ResponseEntity<EntityModel<Account>> withdraw(@PathVariable("id") Integer id, @RequestBody Amount amount) throws NotFoundException {
		Account updatedAccount = service.withdraw(amount.getAmount(), id);
		EntityModel<Account> EntityModel = assembler.toModel(updatedAccount);
		
		return new ResponseEntity<>(EntityModel, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id) throws NotFoundException {
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}







