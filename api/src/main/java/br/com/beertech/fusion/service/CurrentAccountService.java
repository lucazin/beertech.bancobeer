package br.com.beertech.fusion.service;

import java.util.List;

import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.domain.collections.CurrentAccountDocument;

public interface CurrentAccountService {

	 public List<CurrentAccountDocument> listAccounts();
	 
	 public CurrentAccountDocument findByHash(String hash);
	 
	 public CurrentAccountDocument saveAccount(CurrentAccountDocument account);
	 
}
