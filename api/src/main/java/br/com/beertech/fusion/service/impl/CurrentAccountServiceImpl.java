package br.com.beertech.fusion.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.repository.CurrentAccountRepository;
import br.com.beertech.fusion.service.CurrentAccountService;

@Service
public class CurrentAccountServiceImpl implements CurrentAccountService{

	@Autowired
	private CurrentAccountRepository currentAccountRepository;
	
	@Override
	public List<CurrentAccount> listAccounts() {
		return currentAccountRepository.findAll();
	}

	@Override
	public CurrentAccount saveAccount(CurrentAccount account) {
		String hash = UUID.randomUUID().toString();
		account.setHash(hash);
		return currentAccountRepository.save(account);
		
	}

	@Override
	public CurrentAccount findByHash(String hash) {
		return currentAccountRepository.findAccountByHash(hash);
	}

	
}
