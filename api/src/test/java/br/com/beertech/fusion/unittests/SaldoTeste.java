package br.com.beertech.fusion.unittests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.beertech.fusion.controller.dto.OperacaoDto;
import br.com.beertech.fusion.domain.Operacao;
import br.com.beertech.fusion.domain.OperationType;
import br.com.beertech.fusion.domain.Saldo;
import br.com.beertech.fusion.service.SaldoService;
import br.com.beertech.fusion.service.impl.SaldoServiceImpl;

public class SaldoTeste {

    @Test
    void testaSaldo() {
        List<Operacao> operacoes = new ArrayList<>();
        OperacaoDto operacaoDto = new OperacaoDto(OperationType.DEPOSITO, 100.);
        operacoes.add(new Operacao(operacaoDto));
        operacaoDto = new OperacaoDto(OperationType.SAQUE, 25.);
        operacoes.add(new Operacao(operacaoDto));
        operacaoDto = new OperacaoDto(OperationType.SAQUE, 10.);
        operacoes.add(new Operacao(operacaoDto));
        SaldoService saldoService = new SaldoServiceImpl();
        assertThat(saldoService.calcularSaldo(operacoes)).isEqualTo(new Saldo(65.));
    }
}
