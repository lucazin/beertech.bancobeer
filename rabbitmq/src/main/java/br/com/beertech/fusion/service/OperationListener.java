package br.com.beertech.fusion.service;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.beertech.fusion.domain.Operation;

@Service
@RabbitListener(queues = "${spring.rabbitmq.queues.operation}")
public class OperationListener implements MessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(OperationListener.class);

  @Value("${microservices.operation.url}")
  private String urlOperation;

  public void onMessage(Message message) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      String json = new String(message.getBody(), StandardCharsets.UTF_8);

      LOGGER.info("Receive Operation Message {}", json);

      Operation transactionPojo = objectMapper.readValue(json, Operation.class);

      if (new Validator(transactionPojo).validateResponseRMQ()) {
          new RestClient(transactionPojo, urlOperation).sendPostOperationAPI();
      }
    } catch (JsonProcessingException e) {
      LOGGER.error("Error Exception {}", e.getMessage());
    }
  }
}
