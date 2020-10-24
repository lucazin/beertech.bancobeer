package br.com.beertech.fusion.service;

import java.util.List;
import java.util.Optional;

import br.com.beertech.fusion.controller.dto.CurrentAccountDTO;
import br.com.beertech.fusion.controller.dto.CurrentAccountUserDTO;
import br.com.beertech.fusion.domain.CurrentAccount;

public interface CurrentAccountService {

	 List<CurrentAccount> listAccounts();
	 
     Optional<CurrentAccount> findByHash(String hash);
	 
	 public Optional<CurrentAccount> findByCnpj(String cnpj);

	 CurrentAccount saveAccount(CurrentAccountDTO account);

	 CurrentAccount getAccount(Long id, String hash);

    List<CurrentAccountUserDTO> findAllUser();
	 
}
