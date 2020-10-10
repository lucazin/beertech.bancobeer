package br.com.beertech.fusion.controller;

import br.com.beertech.fusion.controller.dto.OperacaoDto;
import br.com.beertech.fusion.domain.Operacao;
import br.com.beertech.fusion.domain.Saldo;
import br.com.beertech.fusion.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/bankbeer")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @GetMapping("/transacoes")
    public List<Operacao> listOperations() {
        return operationService.ListaTransacoes();
    }

    @GetMapping("/saldo")
    public ResponseEntity<Saldo> listSaldo() {
        try
        {
            List<Operacao> Transacoes = operationService.ListaTransacoes();
            Double ValorTotal = 0.0;

            Double Depositos = Transacoes.stream().filter(o -> o.getTipoOperacao() == 1).mapToDouble(o -> o.getValorOperacao()).sum();
            Double Saques = Transacoes.stream().filter(o -> o.getTipoOperacao() == 2).mapToDouble(o -> o.getValorOperacao()).sum();
            ValorTotal =   Depositos - Saques;
            Saldo Saldo = new Saldo(ValorTotal);
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
