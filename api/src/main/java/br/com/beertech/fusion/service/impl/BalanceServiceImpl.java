package br.com.beertech.fusion.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.domain.OperationType;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.domain.DebitCreditType;
import br.com.beertech.fusion.service.BalanceService;

@Service
public class BalanceServiceImpl implements BalanceService {

    @Override
    public Balance calcularSaldo(List<OperationDTO> operacoes) {
        Double valorTotal = 0.0;
        if (operacoes != null && !operacoes.isEmpty()) {
            Double depositos = operacoes.stream()
                    .filter(o -> OperationType.DEPOSITO.equals(o.getTipoOperacao()))
                    .mapToDouble(OperationDTO::getValorOperacao)
                    .sum();
            Double saques = operacoes.stream()
                    .filter(o -> OperationType.SAQUE.equals(o.getTipoOperacao()))
                    .mapToDouble(OperationDTO::getValorOperacao)
                    .sum();
            Double transferenciaDebito = operacoes.stream()
                    .filter(o -> OperationType.TRANSFERENCIA.equals(o.getTipoOperacao()) && DebitCreditType.DEBITO.equals(o.getDebitCredit()))
                    .mapToDouble(OperationDTO::getValorOperacao)
                    .sum();
            Double transferenciaCredito = operacoes.stream()
                    .filter(o -> OperationType.TRANSFERENCIA.equals(o.getTipoOperacao()) && DebitCreditType.CREDITO.equals(o.getDebitCredit()))
                    .mapToDouble(OperationDTO::getValorOperacao)
                    .sum();
            Double pagamentos = operacoes.stream()
                    .filter(o -> OperationType.PAGAMENTO.equals(o.getTipoOperacao()))
                    .mapToDouble(OperationDTO::getValorOperacao)
                    .sum();
            valorTotal = (depositos + transferenciaCredito) - (saques - transferenciaDebito) - pagamentos;
        }
        return new Balance(valorTotal);
    }

}
