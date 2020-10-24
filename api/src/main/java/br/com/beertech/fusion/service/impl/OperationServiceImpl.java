package br.com.beertech.fusion.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import br.com.beertech.fusion.controller.dto.CurrentAccountUserDTO;
import br.com.beertech.fusion.domain.*;
import br.com.beertech.fusion.repository.CurrentAccountUserRepository;
import br.com.beertech.fusion.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.controller.dto.TransferDTO;
import br.com.beertech.fusion.domain.entities.CurrentAccount;
import br.com.beertech.fusion.domain.entities.Operation;
import br.com.beertech.fusion.exception.FusionException;
import br.com.beertech.fusion.repository.OperationRepository;
import br.com.beertech.fusion.service.BalanceService;
import br.com.beertech.fusion.service.CurrentAccountService;
import br.com.beertech.fusion.service.OperationService;
import org.springframework.web.client.RestTemplate;

@Service
public class OperationServiceImpl implements OperationService {

	private OperationRepository operationRepository;

	@Autowired
	private BalanceService balanceService;

	@Autowired
	private CurrentAccountService currentAccountService;

	@Autowired
	private CurrentAccountUserRepository currentAccountUserRepository;

	@Autowired
	private SmsService smsService;

	@Autowired
	public OperationServiceImpl(OperationRepository operationRepository) {
		this.operationRepository = operationRepository;
	}

	@Override
    public OperationDTO newTransaction(OperationDTO operationDTO) {
        Operation operation = new Operation(operationDTO);
        if (operation.getTipoOperacao() == OperationType.DEPOSITO.ID) {
			operation.setDebitCredit(DebitCreditType.CREDITO.id);
		} else if(operation.getTipoOperacao() == OperationType.SAQUE.ID) {
			operation.setDebitCredit(DebitCreditType.DEBITO.id);
		} else if (operation.getTipoOperacao() == OperationType.PAGAMENTO.ID) {
			operation.setDebitCredit(DebitCreditType.DEBITO.id);
		}

		String userphoneNumber = currentAccountUserRepository.findAccountByUserHash(operation.getHash());
		boolean validOperation = false;

		if(operation.getTipoOperacao() != OperationType.DEPOSITO.ID)
		{
			Balance initialBalance = calculateBalanceByHash(operation.getHash());

			if(initialBalance.getSaldo() >= operation.getValorOperacao())
			{
				validOperation = true;
				operationRepository.save(operation);
			}
		}
		else
		{
			operationRepository.save(operation);
			validOperation = true;
		}

		if(userphoneNumber != null  && userphoneNumber.trim().length() >= 11)
			smsService.sendSmsUserOperation(operation,userphoneNumber,validOperation);

        Optional<CurrentAccount> currentAccount = currentAccountService.findByHash(operationDTO.getHash());
        operation.setCurrentAccount(currentAccount.get());
        operation = operationRepository.save(operation);
        operationDTO.setHorarioOperacao(operation.getHorarioOperacao());
        return operationDTO;
	}

	@Override
	public List<Operation> listTransactionByHash(String hash) {
		return operationRepository.listTransactionByHash(hash);
	}

	@Override
	public TransferDTO saveTransfer(TransferDTO transferDTO) throws FusionException {

		String userphoneNumber = currentAccountUserRepository.findAccountByUserHash(transferDTO.getHashOrigin());
		boolean validTransfer = false;

        Optional<CurrentAccount> accountOrigin = currentAccountService.findByHash(transferDTO.getHashOrigin());
        Optional<CurrentAccount> accountDestiny = currentAccountService.findByHash(transferDTO.getHashDestination());

		if (!accountOrigin.isPresent()) {
			throw new FusionException("Conta de origem inexistente!");
		}
		if (!accountDestiny.isPresent()) {
			throw new FusionException("Conta de destino inexistente!");
		}

		Balance sldOrigin = calculateBalanceByHash(transferDTO.getHashOrigin());

		if (sldOrigin.getSaldo() != null && sldOrigin.getSaldo() >= transferDTO.getValue())
		{
			Operation origin = new Operation(transferDTO, OperationType.TRANSFERENCIA, transferDTO.getHashOrigin(), DebitCreditType.DEBITO);
			Operation destiny = new Operation(transferDTO, OperationType.TRANSFERENCIA, transferDTO.getHashDestination(), DebitCreditType.CREDITO);

			operationRepository.save(origin);
			operationRepository.save(destiny);
			validTransfer = true;
		} else {
			throw new FusionException("Saldo insuficiente!");
		}

		smsService.sendSmsUserTransfer(transferDTO,userphoneNumber,validTransfer);
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
