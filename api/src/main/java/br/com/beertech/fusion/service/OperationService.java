package br.com.beertech.fusion.service;

import java.util.List;

import br.com.beertech.fusion.controller.dto.TransferDTO;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.exception.FusionException;

public interface OperationService {

    Operation newTransaction(Operation operacao);

    List<Operation> listTransactionByHash(String hash);

    TransferDTO saveTransfer(TransferDTO transferDTO) throws FusionException;
	
    Balance calculateBalanceByHash(String hash);

    List<Operation> listTransactionByCnpj(String cnpj);

    Balance calculateBalanceByCnpj(String cnpj);

}
