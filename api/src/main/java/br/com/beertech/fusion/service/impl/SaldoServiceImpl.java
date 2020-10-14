package br.com.beertech.fusion.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.domain.OperationType;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.service.SaldoService;

@Service
public class SaldoServiceImpl implements SaldoService {

    @Override
    public Balance calcularSaldo(List<OperationDTO> operacoes) {
        Double valorTotal = 0.0;
        if (operacoes != null && !operacoes.isEmpty()) {
            Double depositos = operacoes.stream()
                    .filter(o -> OperationType.DEPOSITO.equals(o.getTipoOperacao()))
                    .mapToDouble(o -> o.getValorOperacao())
                    .sum();
            Double saques = operacoes.stream()
                    .filter(o -> OperationType.SAQUE.equals(o.getTipoOperacao()))
                    .mapToDouble(o -> o.getValorOperacao())
                    .sum();
            valorTotal = depositos - saques;
        }
        return new Balance(valorTotal);
    }

}
