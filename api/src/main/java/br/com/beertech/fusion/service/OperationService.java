package br.com.beertech.fusion.service;

import java.util.List;

import br.com.beertech.fusion.controller.dto.TransferDTO;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.domain.collections.OperationDocument;
import br.com.beertech.fusion.exception.FusionException;

public interface OperationService {

    OperationDocument newTransaction(OperationDocument operacao);

    List<OperationDocument> ListaTransacoes();
    
    List<OperationDocument> listTransactionByHash(String hash);

    TransferDTO saveTransfer(TransferDTO transferDTO) throws FusionException;
	
	Balance calculateBalance(String hash);
	
}
