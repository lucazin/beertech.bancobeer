package br.com.beertech.fusion.service;

import java.util.List;

import br.com.beertech.fusion.domain.CurrentAccount;

public interface CurrentAccountService {

	 public List<CurrentAccount> listAccounts();
	 
	 public CurrentAccount findByHash(String hash);
	 
	 public CurrentAccount saveAccount(CurrentAccount account);	 
	 
}
