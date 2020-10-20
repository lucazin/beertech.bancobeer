package br.com.beertech.fusion.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import br.com.beertech.fusion.util.Support;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.domain.OperationType;
import br.com.beertech.fusion.service.OperationService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/beercoin")
@ApiIgnore
public class WithdrawController {

    @Autowired
    private OperationService operationService;

    @PostMapping("/withdrawals")
    @ApiOperation(value = "Not avaliable", hidden = true)
    public CompletableFuture<ResponseEntity> saveWithdrawal(@RequestBody OperationDTO withdrawDTO)
            throws ExecutionException, InterruptedException {

        CompletableFuture<ResponseEntity> future = new CompletableFuture<>();
        try {

            future = CompletableFuture.supplyAsync(new Supplier<ResponseEntity>() {
                @Override
                public ResponseEntity<Operation> get() {

                    if(Support.checkToken(withdrawDTO.getTokenOperation()))
                    {
                        Operation operacao = new Operation(new OperationDTO(OperationType.WITHDRAW,
                                withdrawDTO.getValueOperation(), withdrawDTO.getHashOperation(),
                                withdrawDTO.getAgencyOperation(),withdrawDTO.getAccountOperation(),
                                withdrawDTO.getTokenOperation()));

                        return new ResponseEntity<>(operationService.newTransaction(operacao), CREATED);
                    }
                    else
                        return new ResponseEntity<>(
                                new Operation(new OperationDTO(OperationType.NONE,0.0,
                                        "Token not provided",
                                        "Token not provided",
                                        "Token not provided",
                                        "Token not provided")), FORBIDDEN);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(future.get());
    }
}
