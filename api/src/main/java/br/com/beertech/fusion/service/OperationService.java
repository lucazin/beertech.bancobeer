package br.com.beertech.fusion.service;

import java.util.List;

import br.com.beertech.fusion.controller.dto.TransferDTO;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.exception.FusionException;

public interface OperationService {

    Operation newTransaction(Operation operacao);

    void RemoveTransacao(Long idBeer);

    List<Operation> listTransaction(String hash);
    
    List<Operation> listTransactionByHash(String hash);

    TransferDTO saveTransfer(TransferDTO transferDTO) throws FusionException;
	
	Balance calculateBalance(String hash);
	
}
