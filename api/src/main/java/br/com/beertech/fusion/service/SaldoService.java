package br.com.beertech.fusion.service;

import java.util.List;

import br.com.beertech.fusion.controller.dto.OperacaoDto;
import br.com.beertech.fusion.domain.Saldo;

public interface SaldoService {

    Saldo calcularSaldo(List<OperacaoDto> operacoes);
}
