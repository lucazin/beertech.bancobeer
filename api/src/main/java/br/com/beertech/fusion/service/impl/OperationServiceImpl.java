package br.com.beertech.fusion.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.beertech.fusion.controller.dto.TransferDTO;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.domain.DebitCreditType;
import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.domain.OperationType;
import br.com.beertech.fusion.exception.FusionException;
import br.com.beertech.fusion.repository.OperationRepository;
import br.com.beertech.fusion.service.BalanceService;
import br.com.beertech.fusion.service.CurrentAccountService;
import br.com.beertech.fusion.service.OperationService;

@Service
public class OperationServiceImpl implements OperationService {

	private OperationRepository operationRepository;

	@Autowired
	private BalanceService saldoService;

	@Autowired
	private CurrentAccountService currentAccountService;

	@Autowired
	public OperationServiceImpl(OperationRepository operationRepository) {
		this.operationRepository = operationRepository;
	}

	@Override
	public Operation newTransaction(Operation operation) {
		if(operation.getTipoOperacao() == OperationType.DEPOSITO.ID) {
			operation.setDebitCredit(DebitCreditType.CREDITO.id);
		} else if(operation.getTipoOperacao() == OperationType.SAQUE.ID) {
			operation.setDebitCredit(DebitCreditType.DEBITO.id);
		}
		
		return operationRepository.save(operation);
	}

	@Override
	public List<Operation> listTransaction(String hash) {
		return operationRepository.listTransactionByHash(hash);
	}

	@Override
	public void RemoveTransacao(Long idOperation) {
		operationRepository.delete(getOperationById(idOperation));
	}

	private Operation getOperationById(Long idOperation) {
		return operationRepository.getOne(idOperation);
	}

	@Override
	public List<Operation> listTransactionByHash(String hash) {
		return operationRepository.listTransactionByHash(hash);
	}

	@Override
	public TransferDTO saveTransfer(TransferDTO transferDTO) throws FusionException {

		CurrentAccount accountOrigin = currentAccountService.findByHash(transferDTO.getHashOrigin());
		CurrentAccount accountDestiny = currentAccountService.findByHash(transferDTO.getHashDestination());

		if (accountOrigin == null) {
			throw new FusionException("Conta de origem inexistente!");
		}
		if (accountDestiny == null) {
			throw new FusionException("Conta de destino inexistente!");
		}

		Balance sldOrigin = calculateBalance(transferDTO.getHashOrigin());

		if (sldOrigin.getSaldo() != null && sldOrigin.getSaldo() >= transferDTO.getValue()) {
			Operation origin = new Operation(transferDTO, OperationType.TRANSFERENCIA, transferDTO.getHashOrigin(), DebitCreditType.DEBITO);
			Operation destiny = new Operation(transferDTO, OperationType.TRANSFERENCIA, transferDTO.getHashDestination(), DebitCreditType.CREDITO);
			operationRepository.save(origin);
			operationRepository.save(destiny);
		} else {
			throw new FusionException("Saldo insuficiente!");
		}
		return transferDTO;
	}

	@Override
	public Balance calculateBalance(String hash) {

		Balance saldo = new Balance(null);

		try {
			List<Operation> transacoes = listTransactionByHash(hash);
			if (transacoes.isEmpty()) {
				return saldo;
			}
			saldo = saldoService
					.calcularSaldo(transacoes.stream().map(Operation::getOperacaoDto).collect(Collectors.toList()));
			return saldo;
		} catch (Exception e) {
			throw e;
		}
	}

}
