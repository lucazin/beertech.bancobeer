package br.com.beertech.fusion.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import br.com.beertech.fusion.domain.collections.OperationDocument;
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
public class DepositController {

    @Autowired
    private OperationService operationService;

    @PostMapping("/deposits")
    public CompletableFuture<ResponseEntity> saveDeposit(@RequestBody OperationDTO depositoDTO)
            throws ExecutionException, InterruptedException {

        CompletableFuture<ResponseEntity> future = new CompletableFuture<>();
        try {
            // Run a task specified by a Supplier object asynchronously
            future = CompletableFuture.supplyAsync(new Supplier<ResponseEntity>() {
                @Override
                public ResponseEntity<OperationDocument> get() {
                    OperationDocument operacao = new OperationDocument(new OperationDTO(OperationType.DEPOSITO,
                            depositoDTO.getValorOperacao(), depositoDTO.getHash()));
                    return new ResponseEntity<>(operationService.newTransaction(operacao), CREATED);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(future.get());
    }

}
