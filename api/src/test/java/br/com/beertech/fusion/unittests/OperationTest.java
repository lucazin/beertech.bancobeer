package br.com.beertech.fusion.unittests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.domain.OperationType;
import br.com.beertech.fusion.service.SaldoService;
import br.com.beertech.fusion.service.impl.SaldoServiceImpl;

public class OperationTest {
	
	private static final String HASH_ORIGIN = "94fc5d08-8269-4d0d-b45c-41dec9914e9d";
	
    @Test
    void testBalanceDeposit() {
        List<OperationDTO> operacoes = new ArrayList<>();
        operacoes.add(new OperationDTO(OperationType.DEPOSITO, 100.,HASH_ORIGIN));
        SaldoService saldoService = new SaldoServiceImpl();
        assertEquals(saldoService.calcularSaldo(operacoes), new Balance(100.));
    }

    @Test
    void testBalanceWithdraw() {
        List<OperationDTO> operacoes = new ArrayList<>();
        operacoes.add(new OperationDTO(OperationType.SAQUE, 10.,HASH_ORIGIN));
        SaldoService saldoService = new SaldoServiceImpl();
        assertEquals(saldoService.calcularSaldo(operacoes), new Balance(-10.));
    }

    @Test
    void testBalanceOperationVariable() {
        List<OperationDTO> operacoes = new ArrayList<>();
        operacoes.add(new OperationDTO(OperationType.DEPOSITO, 100.,HASH_ORIGIN));
        operacoes.add(new OperationDTO(OperationType.SAQUE, 25.,HASH_ORIGIN));
        operacoes.add(new OperationDTO(OperationType.SAQUE, 10.,HASH_ORIGIN));
        SaldoService saldoService = new SaldoServiceImpl();
        assertEquals(saldoService.calcularSaldo(operacoes), new Balance(65.));
    }

    @Test
    void testBalanceNoOperations() {
        List<OperationDTO> operacoes = new ArrayList<>();
        SaldoService saldoService = new SaldoServiceImpl();
        assertEquals(saldoService.calcularSaldo(operacoes), new Balance(0.));
    }
}
