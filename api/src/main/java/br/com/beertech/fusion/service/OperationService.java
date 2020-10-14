package br.com.beertech.fusion.service;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.controller.dto.TransferDTO;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.exception.FusionException;

import java.util.List;

public interface OperationService {

    Operation newTransaction(Operation operacao);

    void RemoveTransacao(Long idBeer);

    List<Operation> ListaTransacoes();
    
    List<Operation> listTransactionByHash(String hash);

    TransferDTO saveTransfer(TransferDTO transferDTO) throws FusionException;
	
	Balance calculateBalance(String hash);
	
	void publisheOperation(OperationDTO operationDTO);
	
	void publisheTransfer(TransferDTO operationDTO);
}
