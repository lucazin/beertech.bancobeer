package br.com.beertech.fusion.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.beertech.fusion.domain.AccountType;
import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.domain.User;
import br.com.beertech.fusion.domain.security.request.SignupRequest;
import br.com.beertech.fusion.repository.CurrentAccountRepository;
import br.com.beertech.fusion.repository.UserRepository;
import br.com.beertech.fusion.service.CurrentAccountService;
import br.com.beertech.fusion.util.Util;

@Service
public class CurrentAccountServiceImpl implements CurrentAccountService{

	@Autowired
	private CurrentAccountRepository currentAccountRepository;

	@Override
	public List<CurrentAccount> listAccounts() {
		return currentAccountRepository.findAll();
	}

	@Override
	public CurrentAccount findAccountByHash(String hash) { return currentAccountRepository.findByHash(hash); }

	@Override
	public CurrentAccount findByAgencyAccountNumber(String agency,String accountnumber) { return currentAccountRepository.findByAgencyAndAccountNumber(agency,accountnumber); }

	@Override
	public CurrentAccount findAccountById(long id) { return currentAccountRepository.findById(id); }

	@Override
	public CurrentAccount saveAccount(CurrentAccount account) {
		String hash = UUID.randomUUID().toString();
		account.setHash(hash);
		return currentAccountRepository.save(account);
	}

	@Override
	public CurrentAccount saveNewAccountRegister(SignupRequest signUpRequest, UserRepository userRepository, User userRegistered) {

		Set<CurrentAccount> accounts = new HashSet<>();
		if(signUpRequest.getAccountType() == AccountType.CORRENTE_POUPANCA.ID)
		{
			CurrentAccount ContaCorrente = new CurrentAccount();
			ContaCorrente.setHash(UUID.randomUUID().toString());
			ContaCorrente.setAccountType(signUpRequest.getAccountType());  //1 cc  or 2 poupanca
			ContaCorrente.setApprovedLimit(signUpRequest.getApprovedLimit());
			ContaCorrente.setUsedLimit(0.0);
            ContaCorrente.setCreatedAt(Util.getDataAtual());
			ContaCorrente.setDocumentCustomer(signUpRequest.getCnpj());
            ContaCorrente.setAgency(Util.getRandomNumberAccountAgency());
			ContaCorrente.setBalance(0.0);
			ContaCorrente.setActive(1);
            ContaCorrente.setActiveDateUpdate(Util.getDataAtual());
            ContaCorrente.setAccountNumber(Util.getRandomNumberAccountNumber());
			accounts.add(ContaCorrente);

			CurrentAccount ContaPoupanca = new CurrentAccount();
			ContaPoupanca.setHash(UUID.randomUUID().toString());
			ContaPoupanca.setAccountType(AccountType.POUPANCA.ID);
			ContaPoupanca.setApprovedLimit(signUpRequest.getApprovedLimit());
			ContaPoupanca.setUsedLimit(0.0);
            ContaPoupanca.setCreatedAt(Util.getDataAtual());
			ContaPoupanca.setDocumentCustomer(signUpRequest.getCnpj());
			ContaPoupanca.setAgency(ContaCorrente.getAgency());
			ContaPoupanca.setBalance(0.0);
			ContaPoupanca.setActive(1);
            ContaPoupanca.setActiveDateUpdate(Util.getDataAtual());
			ContaPoupanca.setAccountNumber(ContaCorrente.getAccountNumber()+"-500");
			accounts.add(ContaPoupanca);

			userRegistered.setAccountType(signUpRequest.getAccountType());
			userRegistered.setAccounts(accounts);
			userRepository.save(userRegistered);

			return currentAccountRepository.save(ContaCorrente);
		}
		else if(signUpRequest.getAccountType() == AccountType.POUPANCA.ID)
		{
			CurrentAccount ContaPoupanca = new CurrentAccount();
			ContaPoupanca.setHash(UUID.randomUUID().toString());
			ContaPoupanca.setAccountType(AccountType.POUPANCA.ID);
			ContaPoupanca.setApprovedLimit(signUpRequest.getApprovedLimit());
			ContaPoupanca.setUsedLimit(0.0);
            ContaPoupanca.setCreatedAt(Util.getDataAtual());
			ContaPoupanca.setDocumentCustomer(signUpRequest.getCnpj());
			ContaPoupanca.setBalance(0.0);
			ContaPoupanca.setActive(1);
            ContaPoupanca.setActiveDateUpdate(Util.getDataAtual());
            ContaPoupanca.setAgency(Util.getRandomNumberAccountAgency());
            ContaPoupanca.setAccountNumber(Util.getRandomNumberAccountNumber());
			accounts.add(ContaPoupanca);

			userRegistered.setAccountType(signUpRequest.getAccountType());
			userRegistered.setAccounts(accounts);
			userRepository.save(userRegistered);

			return currentAccountRepository.save(ContaPoupanca);
		}
		else
		{
			CurrentAccount ContaCorrente = new CurrentAccount();
			ContaCorrente.setHash(UUID.randomUUID().toString());
			ContaCorrente.setAccountType(AccountType.CORRENTE.ID);
			ContaCorrente.setUsedLimit(0.0);
			ContaCorrente.setApprovedLimit(signUpRequest.getApprovedLimit());
            ContaCorrente.setCreatedAt(Util.getDataAtual());
			ContaCorrente.setDocumentCustomer(signUpRequest.getCnpj());
			ContaCorrente.setBalance(0.0);
			ContaCorrente.setActive(1);
            ContaCorrente.setActiveDateUpdate(Util.getDataAtual());
            ContaCorrente.setAgency(Util.getRandomNumberAccountAgency());
            ContaCorrente.setAccountNumber(Util.getRandomNumberAccountNumber());
			accounts.add(ContaCorrente);

			userRegistered.setAccountType(signUpRequest.getAccountType());
			userRegistered.setAccounts(accounts);
			userRepository.save(userRegistered);

			return currentAccountRepository.save(ContaCorrente);
		}

	}



}
