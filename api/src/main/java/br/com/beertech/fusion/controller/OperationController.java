package br.com.beertech.fusion.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import br.com.beertech.fusion.controller.dto.ReportDTO;
import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.service.CurrentAccountService;
import br.com.beertech.fusion.util.SwaggerDoc;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.controller.dto.TransferDTO;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.service.BalanceService;
import br.com.beertech.fusion.service.OperationService;
import br.com.beertech.fusion.service.PublishTransaction;

import javax.validation.Valid;

@RestController
@RequestMapping("/beercoin")
public class OperationController {

    //region Injection
    @Autowired
    private OperationService operationService;

    @Autowired
    private CurrentAccountService accountService;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private PublishTransaction publisheTransaction;

    //endregion

    //region Transactions
    @GetMapping("/transaction")
    @ApiOperation(value =  SwaggerDoc.transactionTitle,  notes = SwaggerDoc.transactionNotes)
    public CompletableFuture<List<Operation>> listOperations() throws ExecutionException, InterruptedException {

        CompletableFuture<List<Operation>> future = new CompletableFuture<>();
        try
        {
            future = CompletableFuture.supplyAsync(new Supplier<List<Operation>>() {
                @Override
                public List<Operation> get()
                {
                    return operationService.getTransactions();
                }
            });
        }
        catch (Exception e)
        { e.printStackTrace(); }
        return CompletableFuture.completedFuture(future.get());
    }

    @GetMapping("/transaction/{hash}")
    @ApiOperation(value =  SwaggerDoc.transactionHashTitle,  notes = SwaggerDoc.transactionHashNotes)
    public CompletableFuture<List<Operation>> listOperationsHash(@PathVariable String hash) throws ExecutionException, InterruptedException {

        CompletableFuture<List<Operation>> future = new CompletableFuture<>();
        try
        {
            future = CompletableFuture.supplyAsync(new Supplier<List<Operation>>() {
                @Override
                public List<Operation> get()
                {
                    return operationService.getTransactionsByHash(hash);
                }
            });
        }
        catch (Exception e)
        { e.printStackTrace(); }
        return CompletableFuture.completedFuture(future.get());
    }

    @GetMapping("/transaction/{agency}/{accountnumber}")
    @ApiOperation(value =  SwaggerDoc.transactionAccountNumberTitle,  notes = SwaggerDoc.transactionAccountNumberNotes)
    public CompletableFuture<List<Operation>> listOperationsAgencyAccount(@PathVariable String agency,@PathVariable String accountnumber) throws ExecutionException, InterruptedException {

        CompletableFuture<List<Operation>> future = new CompletableFuture<>();
        try
        {
            future = CompletableFuture.supplyAsync(new Supplier<List<Operation>>() {
                @Override
                public List<Operation> get()
                {
                    return operationService.getTransactionsByAgencyAccount(agency,accountnumber);
                }
            });
        }
        catch (Exception e)
        { e.printStackTrace(); }
        return CompletableFuture.completedFuture(future.get());
    }

    //endregion

    //region Balance
    @GetMapping("/totalbalance")
    @ApiOperation(value =  SwaggerDoc.totalbalanceTitle,  notes = SwaggerDoc.totalbalanceNotes)
    public CompletableFuture<ResponseEntity> listbalance() throws ExecutionException, InterruptedException {

        CompletableFuture<ResponseEntity> future = new CompletableFuture<>();
        try
        {
            future = CompletableFuture.supplyAsync(new Supplier<ResponseEntity>() {
                @Override
                public ResponseEntity get()
                {
                    List<CurrentAccount> Accounts = accountService.listAccounts();
                    Balance Saldo = balanceService.getGeneralBalance(Accounts);
                    return new ResponseEntity<>(Saldo, OK);
                }
            });
        }
        catch (Exception e)
        { e.printStackTrace(); }
        return CompletableFuture.completedFuture(future.get());
    }

    @GetMapping("/accountbalance/{hash}")
    @ApiOperation(value =  SwaggerDoc.accountbalanceHashTitle,  notes = SwaggerDoc.accountbalanceHashNotes)
    public CompletableFuture<ResponseEntity> listbalanceByHash(@PathVariable String hash) throws ExecutionException, InterruptedException {

        CompletableFuture<ResponseEntity> future = new CompletableFuture<>();
        try
        {
            future = CompletableFuture.supplyAsync(new Supplier<ResponseEntity>() {
                @Override
                public ResponseEntity get()
                {
                    CurrentAccount Account = accountService.findAccountByHash(hash);
                    Balance balance = balanceService.getAccountBalance(Account);
                    return new ResponseEntity<>(balance, OK);
                }
            });
        }
        catch (Exception e)
        { e.printStackTrace(); }
        return CompletableFuture.completedFuture(future.get());
    }

    @GetMapping("/accountbalance/{agency}/{accountnumber}")
    @ApiOperation(value =  SwaggerDoc.accountbalanceAgencyAccountTitle,  notes = SwaggerDoc.accountbalanceAgencyAccountNotes)
    public CompletableFuture<ResponseEntity> listbalanceByHash(@PathVariable String agency,@PathVariable String accountnumber) throws ExecutionException, InterruptedException {

        CompletableFuture<ResponseEntity> future = new CompletableFuture<>();
        try
        {
            future = CompletableFuture.supplyAsync(new Supplier<ResponseEntity>() {
                @Override
                public ResponseEntity get()
                {
                    CurrentAccount Account = accountService.findByAgencyAccountNumber(agency,accountnumber);
                    Balance balance = balanceService.getAccountBalance(Account);
                    return new ResponseEntity<>(balance, OK);
                }
            });
        }
        catch (Exception e)
        { e.printStackTrace(); }
        return CompletableFuture.completedFuture(future.get());
    }

    //endregion

    //region Repots

    @GetMapping("/bankstatement")
    @ApiOperation(value =  SwaggerDoc.bankstatementTitle,  notes = SwaggerDoc.bankstatementNotes)
    public CompletableFuture<List<Operation>> getReport(@Valid @RequestBody ReportDTO reportDTO) throws ExecutionException, InterruptedException {

        CompletableFuture<List<Operation>> future = new CompletableFuture<>();
        try
        {
            future = CompletableFuture.supplyAsync(new Supplier<List<Operation>>() {
                @Override
                public List<Operation> get()
                {
                    return operationService.getReportByHashAndType(reportDTO.getHashAccount(),reportDTO.getOperationType());
                }
            });
        }
        catch (Exception e)
        { e.printStackTrace(); }
        return CompletableFuture.completedFuture(future.get());
    }

    //endregion

    //region Transfer

    //Put transfer request in queue for rabbitmq process and send to rest for save.
    @PostMapping("/transfers/queue")
    @ApiOperation(value =  SwaggerDoc.transfersQueueTitle,  notes = SwaggerDoc.transfersQueueNotes)
    @PreAuthorize("hasRole('MODERATOR') or hasRole('USER')")
    public CompletableFuture<ResponseEntity> queueTransfer(@RequestBody TransferDTO transferDTO) throws ExecutionException, InterruptedException {

        CompletableFuture<ResponseEntity> future = new CompletableFuture<>();
        try
        {
            future = CompletableFuture.supplyAsync(new Supplier<ResponseEntity>() {
                @Override
                public ResponseEntity get()
                {
                    publisheTransaction.publisheTransfer(transferDTO);
                    return ResponseEntity.status(OK).body("Solicitação de Transferêcia executada!");
                }
            });
        }
        catch (Exception e)
        { e.printStackTrace(); }
        return CompletableFuture.completedFuture(future.get());
    }
    //endregion

    //region Operation

    @PostMapping("/operation/queue")
    @ApiOperation(value =  SwaggerDoc.operationQueueTitle,  notes = SwaggerDoc.operationQueueNotes)
    @PreAuthorize("hasRole('MODERATOR')")
    public CompletableFuture<ResponseEntity> queueOperations(@RequestBody OperationDTO operationDTO) throws ExecutionException, InterruptedException {

        CompletableFuture<ResponseEntity> future = new CompletableFuture<>();
        try
        {
            // Run a task specified by a Supplier object asynchronously
            future = CompletableFuture.supplyAsync(new Supplier<ResponseEntity>() {
                @Override
                public ResponseEntity get()
                {
                    publisheTransaction.publisheOperation(operationDTO);
                    return ResponseEntity.status(OK).body("Solicitação de Operação executada!");
                }
            });
        }
        catch (Exception e)
        { e.printStackTrace(); }
        return CompletableFuture.completedFuture(future.get());
    }

    //endregion

}
