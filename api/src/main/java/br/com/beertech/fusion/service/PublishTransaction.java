package br.com.beertech.fusion.service;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.controller.dto.TransferDTO;

public interface PublishTransaction {

	void publishOperation(OperationDTO operationDTO);
	
	void publishTransfer(TransferDTO operationDTO);
	
}
