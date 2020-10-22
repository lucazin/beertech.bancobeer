package br.com.beertech.fusion.service;

import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.repository.CurrentAccountRepository;
import br.com.beertech.fusion.service.impl.CurrentAccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrentAccountServiceTest {

  private static final Long ACCOUNT_ID = 1L;
  private static final String ACCOUNT_HASH = "hash";
  private static final String ACCOUNT_CNPJ = "12345678912";

  @InjectMocks CurrentAccountServiceImpl accountService;
  @Mock CurrentAccountRepository accountRepository;

  @Test
  void getCurrentAccountByIdAndHashShouldReturnCurrentAccount() {
    when(accountRepository.findAccountByIdAndHash(ACCOUNT_ID, ACCOUNT_HASH))
        .thenReturn(currentAccountBuilder());

    CurrentAccount account = accountService.getAccount(ACCOUNT_ID, ACCOUNT_HASH);

    assertEquals(ACCOUNT_CNPJ, account.getCnpj());
    assertEquals(ACCOUNT_ID, account.getIdConta());
    assertEquals(ACCOUNT_HASH, account.getHash());
  }

  @Test
  void getCurrentAccountByHashShouldReturnCurrentAccount() {
    when(accountRepository.findAccountByHash(ACCOUNT_HASH)).thenReturn(currentAccountBuilder());

    CurrentAccount account = accountService.getAccount(null, ACCOUNT_HASH);

    assertEquals(ACCOUNT_CNPJ, account.getCnpj());
    assertEquals(ACCOUNT_ID, account.getIdConta());
    assertEquals(ACCOUNT_HASH, account.getHash());
  }

  @Test
  void getCurrentAccountByIdShouldReturnCurrentAccount() {
    when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(currentAccountBuilder()));

    CurrentAccount account = accountService.getAccount(ACCOUNT_ID, null);

    assertEquals(ACCOUNT_CNPJ, account.getCnpj());
    assertEquals(ACCOUNT_ID, account.getIdConta());
    assertEquals(ACCOUNT_HASH, account.getHash());
  }

  private static CurrentAccount currentAccountBuilder() {
    CurrentAccount account = new CurrentAccount();
    account.setIdConta(ACCOUNT_ID);
    account.setHash(ACCOUNT_HASH);
    account.setCnpj(ACCOUNT_CNPJ);

    return account;
  }
}
