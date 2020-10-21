package br.com.beertech.fusion.controller;

import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.service.CurrentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/bankbeer")
public class CurrentAccountController {

  @Autowired private CurrentAccountService currentAccountService;

  @GetMapping("/accounts")
  public List<CurrentAccount> listAccounts() {
    return currentAccountService.listAccounts();
  }

  @GetMapping("/current-account")
  public CurrentAccount getCurrentAccount(Long id, String hash) {
    return currentAccountService.getAccount(id, hash);
  }

  @PostMapping("/current-account")
  public CompletableFuture<ResponseEntity> saveCurrentAccount()
      throws ExecutionException, InterruptedException {

    CompletableFuture<ResponseEntity> future = new CompletableFuture<>();
    try {
      future =
          CompletableFuture.supplyAsync(
              new Supplier<ResponseEntity>() {
                @Override
                public ResponseEntity get() {
                  CurrentAccount currentAccount = new CurrentAccount();
                  return new ResponseEntity<>(
                      currentAccountService.saveAccount(currentAccount), CREATED);
                }
              });
    } catch (Exception e) {
      e.printStackTrace();
    }
    return CompletableFuture.completedFuture(future.get());
  }
}
