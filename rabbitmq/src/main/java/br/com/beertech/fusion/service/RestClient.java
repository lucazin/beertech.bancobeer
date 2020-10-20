package br.com.beertech.fusion.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.domain.Transfer;

public class RestClient {

	private RestTemplate restTemplate;
	private Operation operation;
	private Transfer transfer;

	public final String OPERATION_TRANSFER_URL = "http://localhost:8081/beercoin/transfers/operation";
	public final String OPERATION_DEPOSIT_URL = "http://localhost:8081/beercoin/deposits";
	public final String OPERATION_WITHDRAW_URL = "http://localhost:8081/beercoin/withdrawals";
	public final String TRANSFER_URL = "http://localhost:8081/beercoin/transfers/save";


	public RestClient(Operation restObjectParamenter) {
		operation = restObjectParamenter;
	}

	public RestClient(Transfer restObjectParamenter) {
		transfer = restObjectParamenter;
	}

	public void sendPostOperationAPI() {
		try {
			String finalUrl = "";
			this.restTemplate = new RestTemplateBuilder().build();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			Map<String, Object> map = new HashMap<>();
			map.put("typeOperation", operation.getTypeOperation());
			map.put("valueOperation", operation.getValueOperation());
			map.put("hashOperation", operation.getHashOperation());
			map.put("agencyOperation", operation.getAgencyOperation());
			map.put("accountOperation", operation.getAccountOperation());
			map.put("tokenOperation", operation.getTokenOperation());

			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

			if(operation.getTypeOperation().equals("BANKDEPOSIT"))
				finalUrl = OPERATION_DEPOSIT_URL;
			else if(operation.getTypeOperation().equals("WITHDRAW"))
				finalUrl = OPERATION_WITHDRAW_URL;
			else if(operation.getTypeOperation().equals("TRANSFER"))
				finalUrl = OPERATION_TRANSFER_URL;

			ResponseEntity<Operation> response = this.restTemplate.postForEntity(finalUrl, entity, Operation.class);

			if (response.getStatusCode() == HttpStatus.CREATED) {
				System.out.println(response.getBody().toString());
			} else {
				System.out.println("Sem retorno");
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void sendPostTransferAPI() {
		try {
			this.restTemplate = new RestTemplateBuilder().build();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			Map<String, Object> map = new HashMap<>();
			map.put("transferHashOrigin", transfer.getTransferHashOrigin());
			map.put("transferHashDestination", transfer.getTransferHashDestination());
			map.put("trasferValue", transfer.getTrasferValue());
			map.put("tokenOperation",transfer.getTokenOperation());

			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
			ResponseEntity<Transfer> response = this.restTemplate.postForEntity(TRANSFER_URL, entity, Transfer.class);

			if (response.getStatusCode() == HttpStatus.CREATED) {
				System.out.println(response.getBody().toString());
			} else {
				System.out.println("Sem retorno");
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
