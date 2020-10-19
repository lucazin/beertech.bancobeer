package br.com.beertech.fusion.service.impl;

import java.util.List;
import java.util.UUID;

import br.com.beertech.fusion.domain.collections.CurrentAccountDocument;
import br.com.beertech.fusion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.repository.CurrentAccountRepository;
import br.com.beertech.fusion.service.CurrentAccountService;

@Service
public class CurrentAccountServiceImpl implements CurrentAccountService{


	private CurrentAccountRepository currentAccountRepository;

	public CurrentAccountServiceImpl(CurrentAccountRepository currentAccountRepository)
	{
		this.currentAccountRepository  = currentAccountRepository;
	}

	public CurrentAccountServiceImpl(){}
	
	@Override
	public List<CurrentAccountDocument> listAccounts() {
		return currentAccountRepository.findAll();
	}

	@Override
	public CurrentAccountDocument saveAccount(CurrentAccountDocument account) {
		String hash = UUID.randomUUID().toString();
		account.setHash(hash);
		return currentAccountRepository.save(account);
		
	}

	@Override
	public CurrentAccountDocument findByHash(String hash) {
		return currentAccountRepository.findAccountByHash(hash);
	}

	
}
