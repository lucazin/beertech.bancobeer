package br.com.beertech.fusion.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.service.CurrentAccountService;


@RestController
@RequestMapping("/bankbeer")
public class CurrentAccountController {

	@Autowired
	private CurrentAccountService currentAccountService;
	
	@GetMapping("/conta-corrente")
    public List<CurrentAccount> listAccounts() {
        return currentAccountService.listAccounts();
    }
	
    @PostMapping("/conta-corrente")
    public ResponseEntity<CurrentAccount> saveCurrentAccount() {
        CurrentAccount currentAccount = new CurrentAccount();
        return new ResponseEntity<>(currentAccountService.saveAccount(currentAccount),CREATED);
    } 
}
