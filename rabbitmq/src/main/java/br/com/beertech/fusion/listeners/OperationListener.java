package br.com.beertech.fusion.listeners;

import br.com.beertech.fusion.config.BaseAmqpListener;
import br.com.beertech.fusion.domain.Operation;
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
public class OperationListener extends BaseAmqpListener<Operation> {

  private static final Logger LOGGER = LoggerFactory.getLogger(OperationListener.class);

  @Value("${spring.rabbitmq.queues.operation}")
  private String operationQueue;

  private RestClient restClient;

  public OperationListener(AmqpTemplate amqpTemplate, RestClient restClient) {
    super(amqpTemplate);
    this.restClient = restClient;
  }

  @Override
  protected void callService(Operation operation) {
    if (new Validator(operation).validateResponseRMQ()) {
      restClient.sendPostOperationAPI(operation);
    } else {
      LOGGER.error("Error: Some field is not filled");
    }
  }

  @RabbitListener(queues = "${spring.rabbitmq.queues.operation}")
  public void receive(Message message, @Payload final Operation operationMessage)
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    LOGGER.info("Receive Operation Message {}", mapper.writeValueAsString(operationMessage));
    processMessage(operationMessage, message, operationQueue, "operation");
  }
}
