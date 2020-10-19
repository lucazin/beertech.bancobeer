package br.com.beertech.fusion.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.domain.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.service.CurrentAccountService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/bankbeer")
public class CurrentAccountController {

	@Autowired
	private CurrentAccountService currentAccountService;
	
	@GetMapping("/current-account")
    public List<CurrentAccount> listAccounts() {
        return currentAccountService.listAccounts();
    }
	

    @PostMapping("/current-account")
    public CompletableFuture<ResponseEntity> saveCurrentAccount() throws ExecutionException, InterruptedException {

        CompletableFuture<ResponseEntity> future = new CompletableFuture<>();
        try
        {
            future = CompletableFuture.supplyAsync(new Supplier<ResponseEntity>() {
                @Override
                public ResponseEntity get()
                {
                    CurrentAccount currentAccount = new CurrentAccount();
                    return new ResponseEntity<>(currentAccountService.saveAccount(currentAccount),CREATED);
                }
            });
        }
        catch (Exception e)
        { e.printStackTrace(); }
        return CompletableFuture.completedFuture(future.get());
    }
}
