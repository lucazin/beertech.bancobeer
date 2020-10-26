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
import br.com.beertech.fusion.domain.Users;
import br.com.beertech.fusion.exception.FusionException;
import br.com.beertech.fusion.repository.impl.CurrentAccountUserRepositoryImpl;
import br.com.beertech.fusion.service.BillExchangeService;
import br.com.beertech.fusion.service.OperationService;
import br.com.beertech.fusion.service.UserService;

@Service
public class BillExchangeServiceImpl implements BillExchangeService {

	private static final int INDEX_VALUE_BARCODE = 37;

    @Autowired
	private UserService userService;

	@Autowired
	private CurrentAccountUserRepositoryImpl currentAccountUserRepositoryImpl;

	@Autowired
	private OperationService operationService;
	
	@Override
	public void payBillExchange(BillExchangeDTO billExchangeDTO, String token) throws FusionException {

		Optional<Users> user = userService.findUserByToken(token);
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
        return barcode.substring(INDEX_VALUE_BARCODE);
	}

	public Double formatValue(String valueBarCode) throws FusionException {
        String partInt = valueBarCode.substring(0, valueBarCode.length() - 2);
        String partDecimal = valueBarCode.substring(valueBarCode.length() - 2);
        String formatValue = partInt + "." + partDecimal;

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
