package br.com.beertech.fusion.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.beertech.fusion.controller.dto.BillExchangeDTO;
import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.domain.DebitCreditType;
import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.domain.OperationType;
import br.com.beertech.fusion.domain.User;
import br.com.beertech.fusion.exception.FusionException;
import br.com.beertech.fusion.repository.impl.CurrentAccountUserRepositoryImpl;
import br.com.beertech.fusion.service.BillExchangeService;
import br.com.beertech.fusion.service.OperationService;
import br.com.beertech.fusion.service.UserService;

@Service
public class BillExchangeServiceImpl implements BillExchangeService {

	@Autowired
	private UserService userService;

	@Autowired
	private CurrentAccountUserRepositoryImpl currentAccountUserRepositoryImpl;

	@Autowired
	private OperationService operationService;
	
	@Override
	public void payBillExchange(BillExchangeDTO billExchangeDTO, String token) throws FusionException {

		Optional<User> user = userService.findUserByToken(token);
		String hashUser = currentAccountUserRepositoryImpl.findAccountByUser(user.get().getUsername());

		String valueBarcode = getValueBarcode(billExchangeDTO.getBarcode());
		Double valueFormat = formatValue(valueBarcode);

		OperationDTO operationDTO = new OperationDTO();

		operationDTO.setDebitCredit(DebitCreditType.DEBITO);
		operationDTO.setHash(hashUser);
		operationDTO.setValorOperacao(valueFormat);
		operationDTO.setTipoOperacao(OperationType.PAGAMENTO);

        Balance balance = operationService.calculateBalanceByHash(hashUser);

		if (balance.getSaldo() != null && balance.getSaldo() >= valueFormat) {
		
			operationService.newTransaction(new Operation (operationDTO));
		} else {
			throw new FusionException("Saldo insuficiente!");
		}
	}

	public String getValueBarcode(String barcode) {
		return barcode.substring(barcode.length() - 10);
	}

	public Double formatValue(String barCode) throws FusionException {
		
			String value = barCode.substring(barCode.length() - 10);
			String partInt =  value.substring(0, value.length()-2);
			String partDecimal =  value.substring(value.length() - 2);
			String formatValue = partInt + "," + partDecimal;

			DecimalFormat format = new DecimalFormat("#.##");

			Number number = null;
			try {
				number = format.parse(formatValue);
			} catch (ParseException e) {
				throw new FusionException(e.getMessage());
			}
			return number.doubleValue();
		}
}
