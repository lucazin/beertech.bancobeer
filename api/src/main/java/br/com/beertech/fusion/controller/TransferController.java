package br.com.beertech.fusion.controller;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.controller.dto.TransferDTO;
import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.domain.OperationType;
import br.com.beertech.fusion.exception.FusionException;
import br.com.beertech.fusion.service.OperationService;
import br.com.beertech.fusion.util.Support;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.*;

@RestController
@ApiIgnore
@RequestMapping("/beercoin")
public class TransferController {

    @Autowired
    private OperationService operationService;

    @PostMapping("/transfers/operation")
    @ApiOperation(value = "Not avaliable", hidden = true)
    public CompletableFuture<ResponseEntity> saveDeposit(@RequestBody OperationDTO TransferDepositDTO)
            throws ExecutionException, InterruptedException {

        CompletableFuture<ResponseEntity> future = new CompletableFuture<>();
        try {
            future = CompletableFuture.supplyAsync(new Supplier<ResponseEntity>()
            {
                @Override
                public ResponseEntity<Operation> get()
                {
                    if(Support.checkToken(TransferDepositDTO.getTokenOperation()))
                    {
                        Operation BankTransferOperation = new Operation(new OperationDTO(OperationType.TRANSFER,
                                TransferDepositDTO.getValueOperation(), TransferDepositDTO.getHashOperation(),
                                TransferDepositDTO.getAgencyOperation(),TransferDepositDTO.getAccountOperation(),
                                TransferDepositDTO.getTokenOperation()));

                        return new ResponseEntity<>(operationService.newTransaction(BankTransferOperation), CREATED);
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

    //Save transfer from rabbitmq post request
    @PostMapping("/transfers/save")
    public CompletableFuture<ResponseEntity> saveTransfer(@RequestBody TransferDTO transferDTO) throws ExecutionException, InterruptedException {

        CompletableFuture<ResponseEntity> future = new CompletableFuture<>();
        try
        {
            future = CompletableFuture.supplyAsync(new Supplier<ResponseEntity>() {
                @Override
                public ResponseEntity get()
                {
                    ResponseEntity EntityResponse = new ResponseEntity("",NO_CONTENT);
                    try
                    {
                        EntityResponse = new ResponseEntity<>(operationService.saveTransfer(transferDTO), CREATED);
                    }
                    catch (FusionException e)
                    {e.printStackTrace(); }

                    return  EntityResponse;
                }
            });
        }
        catch (Exception e)
        { e.printStackTrace(); }
        return CompletableFuture.completedFuture(future.get());
    }

}
