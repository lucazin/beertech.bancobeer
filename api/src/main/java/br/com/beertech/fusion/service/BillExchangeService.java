package br.com.beertech.fusion.service;

import br.com.beertech.fusion.controller.dto.BillExchangeDTO;
import br.com.beertech.fusion.exception.FusionException;

public interface BillExchangeService {
	
	void payBillExchange(BillExchangeDTO billExchangeDTO, String token) throws FusionException;
	
	String getValueBarcode(String barcode);
	
	Double formatValue(String barCode);

}
