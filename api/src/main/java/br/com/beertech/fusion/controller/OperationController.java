package br.com.beertech.fusion.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.controller.dto.TransferDTO;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.exception.FusionException;
import br.com.beertech.fusion.service.OperationService;
import br.com.beertech.fusion.service.SaldoService;

@RestController
@RequestMapping("/bankbeer")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @Autowired
    private SaldoService saldoService;

    @GetMapping("/transacoes")
    public List<Operation> listOperations() {
        return operationService.ListaTransacoes();
    }

    @GetMapping("/saldo")
    public ResponseEntity<Balance> listSaldo() {
        try
        {
            List<Operation> transacoes = operationService.ListaTransacoes();
            Balance Saldo = saldoService.calcularSaldo(
                    transacoes.stream().map(Operation::getOperacaoDto).collect(Collectors.toList()));
            return new ResponseEntity<>(Saldo, OK);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    
    @GetMapping("/saldo/{hash}")
    public ResponseEntity<Balance> listSaldoConta(@PathVariable String hash) {
    	Balance saldo = operationService.calculateBalance(hash);
    	return new ResponseEntity<>(saldo, OK);
    }
    
    @PostMapping("/operacao/salvar")
    public ResponseEntity<Operation> saveOperations(@RequestBody OperationDTO operacaoDto) {
        Operation operacao = new Operation(operacaoDto);
        return new ResponseEntity<>(operationService.newTransaction(operacao), CREATED);
    }
    
    @PostMapping("/operacao")
    public ResponseEntity<String> queueOperations(@RequestBody OperationDTO operationDTO) {
    	
    	 operationService.publisheOperation(operationDTO);
    	return ResponseEntity.status(OK).body("Solicitação de Operação executada!");
    }

    @PostMapping("/transferencia/salvar")
    public ResponseEntity<TransferDTO> saveTransfer(@RequestBody TransferDTO transferDTO) throws FusionException {
       	return new ResponseEntity<>(operationService.saveTransfer(transferDTO), CREATED);
    }
    
    @PostMapping("/transferencia")
    public ResponseEntity<String> queueTransfer(@RequestBody TransferDTO transferDTO) {
       
    	operationService.publisheTransfer(transferDTO);
        return ResponseEntity.status(OK).body("Solicitação de Transferêcia executada!");
    }

}
