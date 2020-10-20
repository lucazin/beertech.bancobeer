package br.com.beertech.fusion.service;

import java.util.List;

import br.com.beertech.fusion.controller.dto.TransferDTO;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.exception.FusionException;

public interface OperationService {

    List<Operation> getTransactions();

    List<Operation> getTransactionsByHash(String hash);

    List<Operation> getTransactionsByAgencyAccount(String agency,String account);

    Operation newTransaction(Operation operacao);

    List<Operation> getReportByHashAndType(String hash,int operationType);






    TransferDTO saveTransfer(TransferDTO transferDTO) throws FusionException;
}
