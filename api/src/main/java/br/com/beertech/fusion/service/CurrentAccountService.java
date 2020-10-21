package br.com.beertech.fusion.service;

import java.util.List;

import br.com.beertech.fusion.domain.CurrentAccount;

public interface CurrentAccountService {

	 List<CurrentAccount> listAccounts();
	 
	 CurrentAccount findByHash(String hash);
	 
	 CurrentAccount saveAccount(CurrentAccount account);

	 CurrentAccount getAccount(Long id, String hash);
	 
}
