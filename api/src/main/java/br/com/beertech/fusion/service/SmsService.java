package br.com.beertech.fusion.service;

import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.controller.dto.TransferDTO;

public interface SmsService {

  boolean sendSmsUserOperation(Operation operation, String phonenumber,boolean validate);

  boolean sendSmsUserTransfer(TransferDTO transfer, String phonenumber,boolean validate);
}
