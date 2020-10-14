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
	public final String OPERATION_URL = "http://localhost:8081/bankbeer/operacao/salvar";
	public final String TRANSFER_URL = "http://localhost:8081/bankbeer/transferencia/salvar";

	public RestClient(Operation restObjectParamenter) {
		operation = restObjectParamenter;
	}

	public RestClient(Transfer restObjectParamenter) {
		transfer = restObjectParamenter;
	}

	public void sendPostOperationAPI() {
		try {
			this.restTemplate = new RestTemplateBuilder().build();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			Map<String, Object> map = new HashMap<>();
			map.put("tipoOperacao", operation.getTipoOperacao());
			map.put("valorOperacao", operation.getValorOperacao());
			map.put("hash", operation.getHash());

			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
			ResponseEntity<Operation> response = this.restTemplate.postForEntity(OPERATION_URL, entity, Operation.class);

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
			map.put("hashOrigin", transfer.getHashOrigin());
			map.put("hashDestination", transfer.getHashDestination());
			map.put("value", transfer.getValue());

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
