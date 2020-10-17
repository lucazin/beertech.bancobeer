package br.com.beertech.fusion.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.domain.OperationType;
import br.com.beertech.fusion.service.OperationService;

@RestController
@RequestMapping("/bankbeer")
public class WithdrawController {

    @Autowired
    private OperationService operationService;

    @PostMapping("/withdrawals")
    public CompletableFuture<ResponseEntity> saveWithdrawal(@RequestBody OperationDTO saqueDTO)
            throws ExecutionException, InterruptedException {

        CompletableFuture<ResponseEntity> future = new CompletableFuture<>();
        try {
            // Run a task specified by a Supplier object asynchronously
            future = CompletableFuture.supplyAsync(new Supplier<ResponseEntity>() {
                @Override
                public ResponseEntity<Operation> get() {
                    Operation operacao = new Operation(new OperationDTO(OperationType.SAQUE,
                            saqueDTO.getValorOperacao(), saqueDTO.getHash()));
                    return new ResponseEntity<>(operationService.newTransaction(operacao), CREATED);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(future.get());
    }
}
