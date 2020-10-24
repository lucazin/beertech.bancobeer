package br.com.beertech.fusion.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.beertech.fusion.controller.dto.CurrentAccountDTO;
import br.com.beertech.fusion.controller.dto.CurrentAccountUserDTO;
import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.repository.CurrentAccountRepository;
import br.com.beertech.fusion.repository.CurrentAccountUserRepository;
import br.com.beertech.fusion.service.CurrentAccountService;

@Service
public class CurrentAccountServiceImpl implements CurrentAccountService {

  @Autowired private CurrentAccountRepository currentAccountRepository;

	@Autowired
	private CurrentAccountUserRepository currentAccountUserRepository;
	
	 
  @Override
  public List<CurrentAccount> listAccounts() {
    return currentAccountRepository.findAll();
  }

  @Override
  public CurrentAccount saveAccount(CurrentAccountDTO accountDTO) {
    String hash = UUID.randomUUID().toString();

    CurrentAccount account = new CurrentAccount();
    account.setHash(hash);
    account.setCnpj(accountDTO.getCnpj());
    return currentAccountRepository.save(account);
  }

  @Override
  public Optional<CurrentAccount> findByHash(String hash) {
    return currentAccountRepository.findAccountByHash(hash);
	}
	
	@Override
	public Optional<CurrentAccount> findByCnpj(String cnpj) {
		return currentAccountRepository.findAccountByCnpj(cnpj);
  }

	@Override
	public List<CurrentAccountUserDTO> findAllUser() {
		return currentAccountUserRepository.findAccountAllUser();
	}

    @Override
  public CurrentAccount getAccount(Long id, String hash) {
    CurrentAccount currentAccount = new CurrentAccount();

    if (id != null && StringUtils.isNotBlank(hash)) {
      currentAccount = currentAccountRepository.findAccountByIdAndHash(id, hash);
    } else if (StringUtils.isNotBlank(hash)) {
        currentAccount = currentAccountRepository.findAccountByHash(hash).get();
    } else if (id != null) {
        Optional<CurrentAccount> optional = currentAccountRepository.findById(id);
      if (optional.isPresent()) {
        currentAccount = optional.get();
      }
    }

    return currentAccount;
  }
}
