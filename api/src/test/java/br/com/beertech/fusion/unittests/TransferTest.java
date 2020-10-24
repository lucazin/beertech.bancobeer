package br.com.beertech.fusion.unittests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.controller.dto.TransferDTO;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.domain.DebitCreditType;
import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.domain.OperationType;
import br.com.beertech.fusion.exception.FusionException;
import br.com.beertech.fusion.service.BalanceService;
import br.com.beertech.fusion.service.impl.BalanceServiceImpl;
import br.com.beertech.fusion.service.impl.OperationServiceImpl;

//@RunWith(MockitoJUnitRunner.class)
public class TransferTest {

	private static final String HASH_ORIGIN = "94fc5d08-8269-4d0d-b45c-41dec9914e9d";
	private static final String HASH_DESTINATION = "78f885dd-80e8-4846-aae9-0eb3a7ca3b20";

	@Mock
	private TransferDTO transferDTO;

	@Mock
	private OperationServiceImpl operationServiceImpl;

	@Test
	public void testOperationDeposit() {

		List<OperationDTO> operacoes = new ArrayList<>();

		Operation operation = new Operation(new OperationDTO(OperationType.DEPOSITO, 100., HASH_ORIGIN, DebitCreditType.CREDITO));

		OperationServiceImpl operationServiceImpl = mock(OperationServiceImpl.class);
		when(operationServiceImpl.newTransaction(operation)).thenReturn(operation);

		Operation newTransaction = operationServiceImpl.newTransaction(operation);
		operacoes.add(
				new OperationDTO(OperationType.DEPOSITO, newTransaction.getValorOperacao(), newTransaction.getHash(), DebitCreditType.CREDITO));

		BalanceService saldoService = new BalanceServiceImpl();
		assertEquals(saldoService.calcularSaldo(operacoes), new Balance(100.));
	}

	@Test
	public void testOperationWhithdraw() {

		List<OperationDTO> operacoes = new ArrayList<>();

		Operation deposit = new Operation(new OperationDTO(OperationType.DEPOSITO, 500., HASH_ORIGIN,DebitCreditType.CREDITO));

		OperationServiceImpl operationServiceDeposit = mock(OperationServiceImpl.class);
		when(operationServiceDeposit.newTransaction(deposit)).thenReturn(deposit);

		Operation transactionDeposit = operationServiceDeposit.newTransaction(deposit);
		operacoes.add(new OperationDTO(OperationType.DEPOSITO, transactionDeposit.getValorOperacao(),
				transactionDeposit.getHash(), DebitCreditType.CREDITO));

		Operation withdraw = new Operation(new OperationDTO(OperationType.SAQUE, 100., HASH_ORIGIN,DebitCreditType.DEBITO));

		OperationServiceImpl operationServiceWithdraw = mock(OperationServiceImpl.class);
		when(operationServiceWithdraw.newTransaction(withdraw)).thenReturn(withdraw);

		Operation transactionWithdraw = operationServiceWithdraw.newTransaction(withdraw);
		operacoes.add(new OperationDTO(OperationType.SAQUE, transactionWithdraw.getValorOperacao(),
				transactionWithdraw.getHash(),DebitCreditType.DEBITO));

		BalanceService saldoService = new BalanceServiceImpl();
		assertEquals(saldoService.calcularSaldo(operacoes), new Balance(400.));
	}

	@Test
	public void testSaveTransfer() throws FusionException {

		List<OperationDTO> operacoes = new ArrayList<>();

		Operation deposit = new Operation(new OperationDTO(OperationType.DEPOSITO, 500., HASH_ORIGIN,DebitCreditType.CREDITO));

		OperationServiceImpl operationServiceDeposit = mock(OperationServiceImpl.class);
		when(operationServiceDeposit.newTransaction(deposit)).thenReturn(deposit);

		Operation transactionDeposit = operationServiceDeposit.newTransaction(deposit);
		operacoes.add(new OperationDTO(OperationType.DEPOSITO, transactionDeposit.getValorOperacao(),
				transactionDeposit.getHash(), DebitCreditType.CREDITO));

		TransferDTO transferDTO = new TransferDTO();

		transferDTO.setHashOrigin(HASH_ORIGIN);
		transferDTO.setHashDestination(HASH_DESTINATION);
		transferDTO.setValue(100.);

		mock(OperationServiceImpl.class);
		when(operationServiceDeposit.saveTransfer(transferDTO)).thenReturn(transferDTO);

		mock(OperationServiceImpl.class);
		when(operationServiceDeposit.calculateBalanceByHash(HASH_DESTINATION)).thenReturn(new Balance(100.));
	
		assertEquals(operationServiceDeposit.calculateBalanceByHash(HASH_DESTINATION), new Balance(100.));

	}

}
