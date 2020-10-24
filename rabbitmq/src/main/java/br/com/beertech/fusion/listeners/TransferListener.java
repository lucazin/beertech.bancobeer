package br.com.beertech.fusion.listeners;

import br.com.beertech.fusion.config.BaseAmqpListener;
import br.com.beertech.fusion.domain.Transfer;
import br.com.beertech.fusion.service.RestClient;
import br.com.beertech.fusion.service.Validator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class TransferListener extends BaseAmqpListener<Transfer> {

  private static final Logger LOGGER = LoggerFactory.getLogger(TransferListener.class);

  @Value("${spring.rabbitmq.queues.transfer}")
  private String operationQueue;

  private RestClient restClient;

  public TransferListener(AmqpTemplate amqpTemplate, RestClient restClient) {
    super(amqpTemplate);
    this.restClient = restClient;
  }

  @Override
  protected void callService(Transfer transfer) {
    if (new Validator(transfer).validateTransferResponseRMQ()) {
      restClient.sendPostTransferAPI(transfer);
    } else {
      LOGGER.error("Error: Some field is not filled");
    }
  }

  @RabbitListener(queues = "${spring.rabbitmq.queues.transfer}")
  public void receive(Message message, @Payload final Transfer transferMessage)
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    LOGGER.info("Receive Transfer Message {}", mapper.writeValueAsString(transferMessage));
    processMessage(transferMessage, message, operationQueue, "transfer");
  }
}
