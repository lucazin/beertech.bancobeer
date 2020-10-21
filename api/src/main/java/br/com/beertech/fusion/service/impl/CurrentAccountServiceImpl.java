package br.com.beertech.fusion.service.impl;

import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.repository.CurrentAccountRepository;
import br.com.beertech.fusion.service.CurrentAccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CurrentAccountServiceImpl implements CurrentAccountService {

  @Autowired private CurrentAccountRepository currentAccountRepository;

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

  @Override
  public CurrentAccount getAccount(Long id, String hash) {
    CurrentAccount currentAccount = new CurrentAccount();

    if (id != null && StringUtils.isNotBlank(hash)) {
      currentAccount = currentAccountRepository.findAccountByIdAndHash(id, hash);
    }
    else if (StringUtils.isNotBlank(hash)) {
      currentAccount = currentAccountRepository.findAccountByHash(hash);
    }
    else if (id != null) {
      Optional<CurrentAccount> optional = currentAccountRepository.findById(id);
      if (optional.isPresent()) {
        currentAccount = optional.get();
      }
    }

    return currentAccount;
  }
}
