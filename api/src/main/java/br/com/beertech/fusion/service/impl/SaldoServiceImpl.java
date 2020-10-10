package br.com.beertech.fusion.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.beertech.fusion.domain.Operacao;
import br.com.beertech.fusion.domain.Saldo;
import br.com.beertech.fusion.service.SaldoService;

@Service
public class SaldoServiceImpl implements SaldoService {

    @Override
    public Saldo calcularSaldo(List<Operacao> operacoes) {
        Double valorTotal = 0.0;
        Double depositos = operacoes.stream()
                .filter(o -> o.getTipoOperacao() == 1)
                .mapToDouble(o -> o.getValorOperacao())
                .sum();
        Double saques = operacoes.stream()
                .filter(o -> o.getTipoOperacao() == 2)
                .mapToDouble(o -> o.getValorOperacao())
                .sum();
        valorTotal = depositos - saques;
        return new Saldo(valorTotal);
    }

}
