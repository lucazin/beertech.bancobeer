package br.com.beertech.fusion.service;

import java.util.List;
import java.util.Optional;

import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.domain.Users;
import br.com.beertech.fusion.domain.security.request.SignupRequest;
import br.com.beertech.fusion.repository.UserRepository;

public interface CurrentAccountService {

	 List<CurrentAccount> listAccounts();

	 CurrentAccount findAccountByHash(String hash);

	 CurrentAccount findAccountById(long id);

	 CurrentAccount findByAgencyAccountNumber(String agency,String AccountNumber);
	 
	 CurrentAccount saveAccount(CurrentAccount account);

	 CurrentAccount saveNewAccountRegister(SignupRequest signUpRequest, UserRepository userRepository,Users userInfo);

}
