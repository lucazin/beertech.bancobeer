package br.com.beertech.fusion.service;

import java.util.List;

import br.com.beertech.fusion.domain.Operacao;
import br.com.beertech.fusion.domain.Saldo;

public interface SaldoService {

    Saldo calcularSaldo(List<Operacao> operacoes);
}
