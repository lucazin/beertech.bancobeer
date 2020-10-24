package br.com.beertech.fusion.service.impl;

import java.util.List;
import java.util.Optional;
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
	private BalanceService balanceService;

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
	public List<Operation> listTransactionByHash(String hash) {
		return operationRepository.listTransactionByHash(hash);
	}

	@Override
	public TransferDTO saveTransfer(TransferDTO transferDTO) throws FusionException {

        Optional<CurrentAccount> accountOrigin = currentAccountService.findByHash(transferDTO.getHashOrigin());
        Optional<CurrentAccount> accountDestiny = currentAccountService.findByHash(transferDTO.getHashDestination());

		if (!accountOrigin.isPresent()) {
			throw new FusionException("Conta de origem inexistente!");
		}
		if (!accountDestiny.isPresent()) {
			throw new FusionException("Conta de destino inexistente!");
		}

		Balance sldOrigin = calculateBalanceByHash(transferDTO.getHashOrigin());

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
	public Balance calculateBalanceByHash(String hash) {
		try {
            List<Operation> transacoes = listTransactionByHash(hash);
            return calculateBalanceByTransactionList(transacoes);
		} catch (Exception e) {
			throw e;
		}
	}

    private Balance calculateBalanceByTransactionList(List<Operation> transacoes) {
        Balance saldo = new Balance(0.);
        if (!transacoes.isEmpty()) {
            saldo = balanceService
                    .calcularSaldo(transacoes.stream().map(Operation::getOperacaoDto).collect(Collectors.toList()));
        }
        return saldo;
    }

    @Override
    public List<Operation> listTransactionByCnpj(String cnpj) {
        String hash = currentAccountService.findByCnpj(cnpj).map(CurrentAccount::getHash).orElseThrow(null);
        return operationRepository.listTransactionByHash(hash);
    }

    @Override
    public Balance calculateBalanceByCnpj(String cnpj) {
        List<Operation> transactions = listTransactionByCnpj(cnpj);
        return calculateBalanceByTransactionList(transactions);
    }

}
