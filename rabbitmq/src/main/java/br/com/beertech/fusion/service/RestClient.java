package br.com.beertech.fusion.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

  private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);

  private RestTemplate restTemplate;
  private Operation operation;
  private Transfer transfer;
  private String urlOperation;
  private String urlTransfer;

  public RestClient(Operation operation) {
    this.operation = operation;
  }

  public RestClient(Transfer transfer) {
    this.transfer = transfer;
  }

  @Value("${microservices.operation.url}")
  public void setUrlOperation(String urlOperation) {
    this.urlOperation = urlOperation;
  }

  @Value("${microservices.transfer.url}")
  public void setUrlTransfer(String urlTransfer) {
    this.urlTransfer = urlTransfer;
  }

  public void sendPostOperationAPI() {
    try {
      this.restTemplate = new RestTemplateBuilder().build();

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.setBearerAuth(operation.getAuthToken().replace("Bearer",""));

      Map<String, Object> map = new HashMap<>();
      map.put("tipoOperacao", operation.getTipoOperacao());
      map.put("valorOperacao", operation.getValorOperacao());
      map.put("hash", operation.getHash());


      HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
      ResponseEntity<Operation> response =
          this.restTemplate.postForEntity(urlOperation, entity, Operation.class);
      if (response.getStatusCode().equals(HttpStatus.CREATED)) {
        LOGGER.info(Objects.requireNonNull(response.getBody()).toString());
      } else {
        LOGGER.info("Sem retorno");
      }
    } catch (Exception e) {
      LOGGER.error("Error Exception: {}", e.getMessage());
    }
  }

  public void sendPostTransferAPI() {
    try {
      this.restTemplate = new RestTemplateBuilder().build();

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.setBearerAuth(transfer.getAuthToken().replace("Bearer",""));

      Map<String, Object> map = new HashMap<>();
      map.put("hashOrigin", transfer.getHashOrigin());
      map.put("hashDestination", transfer.getHashDestination());
      map.put("value", transfer.getValue());


      HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
      ResponseEntity<Transfer> response =
          this.restTemplate.postForEntity(urlTransfer, entity, Transfer.class);

      if (response.getStatusCode().equals(HttpStatus.CREATED)) {
        LOGGER.info(Objects.requireNonNull(response.getBody()).toString());
      } else {
        LOGGER.info("Sem retorno");
      }
    } catch (Exception e) {
      LOGGER.error("Error Exception: {}", e.getMessage());
    }
  }
}
