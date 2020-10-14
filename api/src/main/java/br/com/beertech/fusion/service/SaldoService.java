package br.com.beertech.fusion.service;

import java.util.List;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.domain.Balance;

public interface SaldoService {

    Balance calcularSaldo(List<OperationDTO> operacoes);
}
