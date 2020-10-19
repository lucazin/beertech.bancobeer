package br.com.beertech.fusion.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import br.com.beertech.fusion.domain.collections.CurrentAccountDocument;
import br.com.beertech.fusion.domain.collections.OperationDocument;
import br.com.beertech.fusion.repository.CurrentAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.beertech.fusion.controller.dto.TransferDTO;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.domain.CurrentAccount;
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

	public OperationServiceImpl(OperationRepository operationRepository) {
		this.operationRepository = operationRepository;
	}

	public OperationServiceImpl(){}

	@Autowired
	private BalanceService saldoService;

	@Autowired
	private CurrentAccountService currentAccountService;




	@Override
	public OperationDocument newTransaction(OperationDocument operation) {
		return operationRepository.save(operation);
	}

	@Override
	public List<OperationDocument> ListaTransacoes() {
		return operationRepository.findAll();
	}

	@Override
	public List<OperationDocument> listTransactionByHash(String hash) {
		return operationRepository.findAllByHash(hash);
	}

	@Override
	public TransferDTO saveTransfer(TransferDTO transferDTO) throws FusionException {

		CurrentAccountDocument accountOrigin = currentAccountService.findByHash(transferDTO.getHashOrigin());
		CurrentAccountDocument accountDestiny = currentAccountService.findByHash(transferDTO.getHashDestination());

		if (accountOrigin == null) {
			throw new FusionException("Conta de origem inexistente!");
		}
		if (accountDestiny == null) {
			throw new FusionException("Conta de destino inexistente!");
		}

		Balance sldOrigin = calculateBalance(transferDTO.getHashOrigin());

		if (sldOrigin.getSaldo() != null && sldOrigin.getSaldo() >= transferDTO.getValue()) {
			OperationDocument origin = new OperationDocument(transferDTO, OperationType.SAQUE, transferDTO.getHashOrigin());
			OperationDocument destiny = new OperationDocument(transferDTO, OperationType.DEPOSITO, transferDTO.getHashDestination());
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
			List<OperationDocument> transacoes = listTransactionByHash(hash);
			if (transacoes.isEmpty()) {
				return saldo;
			}
			saldo = saldoService
					.calcularSaldo(transacoes.stream().map(OperationDocument::getOperacaoDto).collect(Collectors.toList()));
			return saldo;
		} catch (Exception e) {
			throw e;
		}
	}

	

}
