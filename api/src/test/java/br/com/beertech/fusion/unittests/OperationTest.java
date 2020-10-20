package br.com.beertech.fusion.unittests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.domain.OperationType;
import br.com.beertech.fusion.service.BalanceService;
import br.com.beertech.fusion.service.impl.BalanceServiceImpl;

public class OperationTest {
	
	private static final String HASH_ORIGIN = "94fc5d08-8269-4d0d-b45c-41dec9914e9d";
	
    @Test
    public void testBalanceDeposit() {
        List<OperationDTO> operacoes = new ArrayList<>();
        operacoes.add(new OperationDTO(OperationType.BANKDEPOSIT, 100.,HASH_ORIGIN,"000","000","000"));
        BalanceService saldoService = new BalanceServiceImpl();
        //assertEquals(saldoService.calcularSaldo(operacoes), new Balance(100.));
    }

    @Test
    public void testBalanceWithdraw() {
        List<OperationDTO> operacoes = new ArrayList<>();
        operacoes.add(new OperationDTO(OperationType.WITHDRAW, 10.,HASH_ORIGIN,"000","000","000"));
        BalanceService saldoService = new BalanceServiceImpl();
        //assertEquals(saldoService.calcularSaldo(operacoes), new Balance(-10.));
    }

    @Test
    public void testBalanceOperationVariable() {
        List<OperationDTO> operacoes = new ArrayList<>();
        operacoes.add(new OperationDTO(OperationType.BANKDEPOSIT, 100.,HASH_ORIGIN,"000","000","000"));
        operacoes.add(new OperationDTO(OperationType.WITHDRAW, 25.,HASH_ORIGIN,"000","000","000"));
        operacoes.add(new OperationDTO(OperationType.WITHDRAW, 10.,HASH_ORIGIN,"000","000","000"));
        BalanceService saldoService = new BalanceServiceImpl();
       // assertEquals(saldoService.calcularSaldo(operacoes), new Balance(65.));
    }

    @Test
    public void testBalanceNoOperations() {
        List<OperationDTO> operacoes = new ArrayList<>();
        BalanceService saldoService = new BalanceServiceImpl();
       // assertEquals(saldoService.calcularSaldo(operacoes), new Balance(0.));
    }
}
