package br.com.beertech.fusion.controller;

import br.com.beertech.fusion.controller.dto.OperacaoDto;
import br.com.beertech.fusion.domain.Operacao;
import br.com.beertech.fusion.domain.Saldo;
import br.com.beertech.fusion.service.OperationService;
import br.com.beertech.fusion.service.SaldoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/bankbeer")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @Autowired
    private SaldoService saldoService;

    @GetMapping("/transacoes")
    public List<Operacao> listOperations() {
        return operationService.ListaTransacoes();
    }

    @GetMapping("/saldo")
    public ResponseEntity<Saldo> listSaldo() {
        try
        {
            List<Operacao> transacoes = operationService.ListaTransacoes();
            Saldo Saldo = saldoService.calcularSaldo(
                    transacoes.stream().map(Operacao::getOperacaoDto).collect(Collectors.toList()));
            return new ResponseEntity<>(Saldo, OK);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @PostMapping("/operacao")
    public ResponseEntity<Operacao> saveOperations(@RequestBody OperacaoDto operacaoDto) {
        Operacao operacao = new Operacao(operacaoDto);
        return new ResponseEntity<>(operationService.NovaTransacao(operacao), CREATED);
    }


}
