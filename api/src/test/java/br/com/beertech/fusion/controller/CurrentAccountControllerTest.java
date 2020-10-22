package br.com.beertech.fusion.controller;

import br.com.beertech.fusion.controller.dto.CurrentAccountDTO;
import br.com.beertech.fusion.controller.dto.CurrentAccountUserDTO;
import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.service.CurrentAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CurrentAccountControllerTest {

  private static final Long ACCOUNT_ID = 1L;
  private static final String ACCOUNT_HASH = "hash";
  private static final String ACCOUNT_CNPJ = "12345678912";
  private static final String BASE_URL = "/bankbeer/current-account";

  @InjectMocks private CurrentAccountController currentAccountController;
  @Mock private CurrentAccountService currentAccountService;

  private MockMvc mockMvc;

  @BeforeEach
  void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(currentAccountController).build();
  }

  @Test
  void getAllCurrentAccountShouldBeSuccess() throws Exception {
    when(currentAccountService.listAccounts())
        .thenReturn(Collections.singletonList(currentAccountBuilder()));

    this.mockMvc.perform(get(BASE_URL + "/allAccount")).andExpect(status().isOk()).andReturn();
  }

  @Test
  void getCurrentAccountByIdAndHashShouldBeSuccess() throws Exception {

    when(currentAccountService.getAccount(ACCOUNT_ID, ACCOUNT_HASH))
        .thenReturn(currentAccountBuilder());

    this.mockMvc
        .perform(get(BASE_URL).param("hash", ACCOUNT_HASH).param("id", ACCOUNT_ID.toString()))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  void postCurrentAccountShouldBeSuccess() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    CurrentAccountDTO accountDTO = new CurrentAccountDTO();
    accountDTO.setCnpj(ACCOUNT_CNPJ);

    when(currentAccountService.saveAccount(any())).thenReturn(currentAccountBuilder());

    this.mockMvc
        .perform(
            post(BASE_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(accountDTO)))
        .andExpect(status().isCreated())
        .andReturn();
  }

  @Test
  void getCurrentAccountListShouldBeSuccess() throws Exception {
    CurrentAccountUserDTO accountUserDTO = new CurrentAccountUserDTO();
    accountUserDTO.setCnpj(ACCOUNT_CNPJ);
    accountUserDTO.setHash(ACCOUNT_HASH);
    accountUserDTO.setNome("Account Name");
    accountUserDTO.setEmail("email@email.com");

    when(currentAccountService.findAllUser()).thenReturn(Collections.singletonList(accountUserDTO));

    this.mockMvc.perform(get(BASE_URL + "/accountList")).andExpect(status().isOk()).andReturn();
  }

  public static CurrentAccount currentAccountBuilder() {
    CurrentAccount account = new CurrentAccount();
    account.setIdConta(ACCOUNT_ID);
    account.setHash(ACCOUNT_HASH);
    account.setCnpj(ACCOUNT_CNPJ);

    return account;
  }
}
