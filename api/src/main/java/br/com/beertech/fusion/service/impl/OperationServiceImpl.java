package br.com.beertech.fusion.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.repository.CurrentAccountRepository;
import br.com.beertech.fusion.service.PublishTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import static org.springframework.http.HttpStatus.OK;

@Service
public class OperationServiceImpl implements OperationService {

	//region Constructor / Injection
	@Autowired
	private OperationRepository operationRepository;

	@Autowired
	private CurrentAccountRepository AccountRepository;

	@Autowired
	private CurrentAccountService currentAccountService;

	@Autowired
	private PublishTransaction publisheTransaction;

	@Autowired
	public OperationServiceImpl(OperationRepository operationRepository) {
		this.operationRepository = operationRepository;
	}
	//endregion

	//region Done
	@Override
	public Operation newTransaction(Operation operation) {

		if(operation.getOperationType() == OperationType.BANKDEPOSIT.ID)
		{
			CurrentAccount PersonalAccount = currentAccountService.findByAgencyAccountNumber(operation.getOperationAgency(),operation.getOperationAccountNumber());

			if(PersonalAccount != null)
			{
				if (PersonalAccount.getApprovedLimit() > 0)
				{

					if(PersonalAccount.getBalance() == 0 && PersonalAccount.getUsedLimit() == PersonalAccount.getApprovedLimit())
					{
						//Limit Reached
						PersonalAccount.setBalance(0.0);
						PersonalAccount.setUsedLimit(PersonalAccount.getUsedLimit() - operation.getOperationValue());
						AccountRepository.save(PersonalAccount);
						operationRepository.save(operation);
					}
					else if(PersonalAccount.getBalance() == 0 &&
							(PersonalAccount.getUsedLimit() < PersonalAccount.getApprovedLimit() && PersonalAccount.getUsedLimit() > 0))
					{
						PersonalAccount.setBalance(0.0);
						PersonalAccount.setUsedLimit(PersonalAccount.getUsedLimit() - operation.getOperationValue());
						AccountRepository.save(PersonalAccount);
						operationRepository.save(operation);
					}
					else if(PersonalAccount.getBalance() == 0 && PersonalAccount.getUsedLimit() <= 0)
					{
						PersonalAccount.setBalance(PersonalAccount.getBalance() + operation.getOperationValue());
						PersonalAccount.setUsedLimit(0.0);
						AccountRepository.save(PersonalAccount);
						operationRepository.save(operation);
					}
					else if(PersonalAccount.getBalance() > 0 && PersonalAccount.getUsedLimit() == 0)
					{
						PersonalAccount.setBalance(PersonalAccount.getBalance() + operation.getOperationValue());
						PersonalAccount.setUsedLimit(0.0);
						AccountRepository.save(PersonalAccount);
						operationRepository.save(operation);
					}
				}
				else
				{
					PersonalAccount.setBalance(PersonalAccount.getBalance() + operation.getOperationValue());
					AccountRepository.save(PersonalAccount);
					operationRepository.save(operation);
				}
			}

		}
		else if(operation.getOperationType() == OperationType.WITHDRAW.ID)
		{
			CurrentAccount PersonalAccount = currentAccountService.findByAgencyAccountNumber(operation.getOperationAgency(),operation.getOperationAccountNumber());
			if(PersonalAccount != null)
			{
				if(PersonalAccount.getBalance() == 0)
				{
					if(PersonalAccount.getApprovedLimit() > 0)
					{
						PersonalAccount.setUsedLimit(PersonalAccount.getUsedLimit()+operation.getOperationValue());
						AccountRepository.save(PersonalAccount);
						operationRepository.save(operation);
					}
				}
				else
				{
					if(PersonalAccount.getApprovedLimit() > 0)
					{
						if(PersonalAccount.getApprovedLimit() >= (PersonalAccount.getBalance() - operation.getOperationValue()))
						{
							PersonalAccount.setBalance(PersonalAccount.getBalance() - operation.getOperationValue());
							AccountRepository.save(PersonalAccount);
							operationRepository.save(operation);
						}
					}
					else
					{
						if((PersonalAccount.getBalance() - operation.getOperationValue()) >= 0)
						{
							PersonalAccount.setBalance(PersonalAccount.getBalance() - operation.getOperationValue());
							AccountRepository.save(PersonalAccount);
							operationRepository.save(operation);
						}
					}
				}
			}

		}
		else if(operation.getOperationType() == OperationType.TRANSFER.ID)
		{
			operationRepository.save(operation);
		}

		return operation;
	}

	@Override
	public List<Operation> getTransactions() { return operationRepository.findAll(); }

	@Override
	public List<Operation> getTransactionsByHash(String hash) { return operationRepository.findAllByOperationHash(hash); }

	@Override
	public List<Operation> getTransactionsByAgencyAccount(String agency,String Account) {
		return operationRepository.findAllByOperationAgencyAndOperationAccountNumber(agency,Account); }

	@Override
	public List<Operation> getReportByHashAndType(String hash,int operationType) {

		if(operationType > 0)
			return operationRepository.findAllByOperationHashAndOperationType(hash,operationType);
		else
			return operationRepository.findAllByOperationHash(hash);
	}

	@Override
	public TransferDTO saveTransfer(TransferDTO transferDTO) throws FusionException
	{
		CurrentAccount PersonalAccountOrigin = currentAccountService.findAccountByHash(transferDTO.getTransferHashOrigin());
		CurrentAccount PersonalAccountDestination = currentAccountService.findAccountByHash(transferDTO.getTransferHashDestination());
		if(PersonalAccountOrigin != null && PersonalAccountDestination != null)
		{
			//Transferencia
			if(PersonalAccountOrigin.getBalance() > 0)
			{
				PersonalAccountOrigin.setBalance(PersonalAccountOrigin.getBalance() - transferDTO.getTrasferValue());
				AccountRepository.save(PersonalAccountOrigin);

				publisheTransaction.publisheOperation(new OperationDTO(
						OperationType.TRANSFER, transferDTO.getTrasferValue(), transferDTO.getTransferHashOrigin(),
						PersonalAccountOrigin.getAgency(),PersonalAccountOrigin.getAccountNumber(),transferDTO.getTokenOperation()

				));

				publisheTransaction.publisheOperation(new OperationDTO(
						OperationType.BANKDEPOSIT, transferDTO.getTrasferValue(), transferDTO.getTransferHashDestination(),
						PersonalAccountDestination.getAgency(),PersonalAccountDestination.getAccountNumber(),transferDTO.getTokenOperation()
				));
			}
		}
		else
			throw new FusionException("Conta de origem / destino inexistente!");

		return transferDTO;
	}


	//endregion


}
