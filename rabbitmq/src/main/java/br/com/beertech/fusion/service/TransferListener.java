package br.com.beertech.fusion.service;

import br.com.beertech.fusion.domain.Transfer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class TransferListener implements MessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(TransferListener.class);
  private RestClient restClient;

  @Autowired
  public TransferListener(RestClient restClient) {
    this.restClient = restClient;
  }

  public TransferListener() {}

  @RabbitListener(queues = "${spring.rabbitmq.queues.transfer}")
  public void onMessage(Message message) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      String json = new String(message.getBody(), StandardCharsets.UTF_8);

      LOGGER.info("Receive Transfer Message {}", json);

      Transfer transactionPojo = objectMapper.readValue(json, Transfer.class);

      if (new Validator(transactionPojo).validateTransferResponseRMQ()) {
        restClient.sendPostTransferAPI(transactionPojo);
      }
    } catch (JsonProcessingException e) {
      LOGGER.error(e.getMessage());
    }
  }
}
