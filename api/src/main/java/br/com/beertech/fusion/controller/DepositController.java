package br.com.beertech.fusion.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.domain.DebitCreditType;
import br.com.beertech.fusion.domain.OperationType;
import br.com.beertech.fusion.service.OperationService;
import br.com.beertech.fusion.service.PublishTransaction;

@RestController
@RequestMapping("/bankbeer")
public class DepositController {

    @Autowired
    private OperationService operationService;

    @Autowired
    private PublishTransaction publisheTransaction;
    
    @PostMapping("/deposits")
    @PreAuthorize("hasRole('MODERATOR')")
    public CompletableFuture<ResponseEntity> saveDeposit(@RequestBody OperationDTO depositoDTO)
            throws ExecutionException, InterruptedException {

        CompletableFuture<ResponseEntity> future = new CompletableFuture<>();
        try {
            // Run a task specified by a Supplier object asynchronously
            future = CompletableFuture.supplyAsync(new Supplier<ResponseEntity>() {
                @Override
                public ResponseEntity<String> get() {

                   	publisheTransaction.publisheOperation(new OperationDTO(OperationType.DEPOSITO,
                            depositoDTO.getValorOperacao(), depositoDTO.getHash(), DebitCreditType.CREDITO));
                    return ResponseEntity.status(OK).body("Solicitação de Deposito executada!");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(future.get());
    }
}
