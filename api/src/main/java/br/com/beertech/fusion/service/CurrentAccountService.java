package br.com.beertech.fusion.service;

import java.util.List;

import br.com.beertech.fusion.domain.CurrentAccountDocument;
import br.com.beertech.fusion.domain.UserDocument;
import org.springframework.stereotype.Component;

@Component
public interface CurrentAccountService {

	 public List<CurrentAccountDocument> listAccounts();
	 
	 public CurrentAccountDocument findByHash(String hash);
	 
	 public CurrentAccountDocument saveAccount(CurrentAccountDocument account);

	 public void SaveUser(UserDocument User);
	 
}
