package br.com.beertech.fusion.Rest;

import br.com.beertech.fusion.domain.operacao;
import br.com.beertech.fusion.domain.operacao_saldo;
import br.com.beertech.fusion.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/Bank")
public class OperationInit {

    @Autowired
    private OperationService operationService;

    @GetMapping("/Transacoes")
    public List<operacao> listOperations() {
        return operationService.ListaTransacoes();
    }

    @GetMapping("/Saldo")
    public ResponseEntity<operacao_saldo> listSaldo() {
        try
        {
            List<operacao> Transacoes = operationService.ListaTransacoes();
            Double ValorTotal = 0.0;

            Double Depositos = Transacoes.stream().filter(o -> o.getTipoOperacao() == 1).mapToDouble(o -> o.getValorOperacao()).sum();
            Double Saques = Transacoes.stream().filter(o -> o.getTipoOperacao() == 2).mapToDouble(o -> o.getValorOperacao()).sum();
            ValorTotal =   Depositos - Saques;
            operacao_saldo Saldo = new operacao_saldo(ValorTotal);
            return new ResponseEntity<>(Saldo, OK);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @PostMapping("/NovaTransacao")
    public ResponseEntity<operacao> saveOperations(@RequestBody operacao operacao) {
        operacao.setHorarioOperacao(operacao.GetDataAtual());
        return new ResponseEntity<>(operationService.NovaTransacao(operacao), CREATED);
    }

    @DeleteMapping("/Remover/{id}")
    public void removeOperation(@PathVariable Long id) {
        operationService.RemoveTransacao(id);
    }

}
