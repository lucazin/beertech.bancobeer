package br.com.beertech.fusion.service;

import java.util.List;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.domain.CurrentAccount;

public interface BalanceService {

    //Balance calcularSaldo(List<OperationDTO> operacoes);

    Balance getGeneralBalance(List<CurrentAccount> accounts);

    Balance getAccountBalance(CurrentAccount accounts);

}
