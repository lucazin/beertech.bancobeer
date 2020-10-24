package br.com.beertech.fusion.service.impl;

import br.com.beertech.fusion.controller.dto.TransferDTO;
import br.com.beertech.fusion.domain.*;
import br.com.beertech.fusion.domain.security.roles.EnumRole;
import br.com.beertech.fusion.exception.FusionException;
import br.com.beertech.fusion.repository.UserRepository;
import br.com.beertech.fusion.repository.UserRoleRepository;
import br.com.beertech.fusion.repository.impl.CurrentAccountUserRepositoryImpl;
import br.com.beertech.fusion.service.SmsService;
import br.com.beertech.fusion.service.UserService;
import br.com.beertech.fusion.service.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class SmsServiceImpl implements SmsService {

	@Value("${legacypoc.app.tokenSMS}")
	private String smstoken;

	@Value("${legacypoc.app.tokenUrl}")
	private String smstokenUrl;

	@Override
	public boolean sendSmsUserOperation(Operation operation, String phonenumber,boolean validate) {

		boolean retorno = false;
		try {
			String smsMessage = "";

			if(validate)
			{
				if(operation.getTipoOperacao() == OperationType.DEPOSITO.ID)
					smsMessage = "Depósito efetuado com sucesso no valor de: "+operation.getValorOperacao()+ "";
				else if(operation.getTipoOperacao() == OperationType.SAQUE.ID)
					smsMessage = "Saque efetuado com sucesso no valor de: "+operation.getValorOperacao()+ "";
				else
					smsMessage = "Pagamento efetuado com sucesso no valor de: "+operation.getValorOperacao()+ "";
			}
			else
			{
				if(operation.getTipoOperacao() == OperationType.DEPOSITO.ID)
					smsMessage = "Não foi possível concluir a operação de depósito!";
				else if(operation.getTipoOperacao() == OperationType.SAQUE.ID)
					smsMessage = "Não foi possível concluir a operação de saque!";
				else
					smsMessage = "Não foi possível realizar este pagamento!";
			}

			ResponseEntity<SmsSend> response = processSMS(phonenumber,smsMessage);

			if (response.getStatusCode() == HttpStatus.CREATED) {
				retorno = true;
			} else {
				retorno = false;
			}
		}
		catch (Exception e) {
			retorno = false;
			throw e;
		}
		return  retorno;

	}

	@Override
	public boolean sendSmsUserTransfer(TransferDTO transfer, String phonenumber,boolean validate) {

		String smsMessage = "";
		boolean retorno = false;

		if(validate)
			smsMessage = "Transferência realizada com sucesso no valor de: "+transfer.getValue()+ "";
		else
			smsMessage = "Não foi possível realizar a transfêrencia!";

		try
		{
			ResponseEntity<SmsSend> response = processSMS(phonenumber,smsMessage);
			if (response.getStatusCode() == HttpStatus.CREATED) {
				retorno = true;
			} else {
				retorno = false;
			}
		}
		catch (Exception e) {
			retorno = false;
			throw e;
		}
		return retorno;
	}

	public ResponseEntity<SmsSend> processSMS(String phonenumber, String smsMessage )
	{
		RestTemplate restTemplate = new RestTemplateBuilder().build();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Access-Token",smstoken);

		Map<String, Object> map = new HashMap<>();
		map.put("numero_destino", phonenumber);
		map.put("mensagem", smsMessage);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
		ResponseEntity<SmsSend> response = restTemplate.postForEntity(smstokenUrl, entity, SmsSend.class);

		return  response;
	}

}
