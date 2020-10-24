package br.com.beertech.fusion.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseAmqpListener<T> {

  private static final Logger LOGGER = LogManager.getLogger(BaseAmqpListener.class);
  private static final String HEADER_RETRIES = "retries";
  private Integer maxRetry;
  private final AmqpTemplate amqpTemplate;

  @Autowired
  public BaseAmqpListener(AmqpTemplate amqpTemplate) {
    this.amqpTemplate = amqpTemplate;
  }

  @Value("${amqp.maxRetry}")
  public void setMaxRetry(Integer maxRetry) {
    this.maxRetry = maxRetry;
  }

  protected abstract void callService(T object);

  public void processMessage(T object, Message message, String queue, String routingKey) {
    String exchangeName = queue + "-exchange-retry";

    try {
      callService(object);
    } catch (Exception ex) {
      int retry = 1;
      if (message.getMessageProperties().getHeaders().containsKey(HEADER_RETRIES)) {
        retry = message.getMessageProperties().getHeader(HEADER_RETRIES);
      }

      LOGGER.warn("Error during processing. Retry: {}, Exception: {}", retry, ex.getMessage());

      if (retry < maxRetry) {
        retry++;
        message.getMessageProperties().getHeaders().put(HEADER_RETRIES, retry);

      } else {
        LOGGER.error("Max number of retries exhausted. Exception: {}", ex.getMessage(), ex);
        exchangeName = queue + "-exchange-dlq";
      }
      amqpTemplate.send(exchangeName, routingKey, message);
    }
  }
}
