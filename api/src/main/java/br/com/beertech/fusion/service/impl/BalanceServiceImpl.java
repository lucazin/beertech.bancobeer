package br.com.beertech.fusion.service.impl;

import java.util.List;

import br.com.beertech.fusion.domain.CurrentAccount;
import org.springframework.stereotype.Service;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.domain.OperationType;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.service.BalanceService;

@Service
public class BalanceServiceImpl implements BalanceService {


       @Override
    public Balance getGeneralBalance(List<CurrentAccount> accounts)
    {
        Double BalanceAccount = accounts.stream()
                .filter(o -> o.getActive() == 1)
                .mapToDouble(CurrentAccount::getBalance)
                .sum();

        return new Balance(BalanceAccount);
    }

    @Override
    public Balance getAccountBalance(CurrentAccount accounts)
    {
        return new Balance(accounts.getBalance());
    }
}
