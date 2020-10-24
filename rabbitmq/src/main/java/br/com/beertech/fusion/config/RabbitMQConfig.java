package br.com.beertech.fusion.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMQConfig {

  private static final String DEADLETTER_SUFFIX = "-dlq";
  private static final String RETRY_SUFFIX = "-retry";
  private static final String EXCHANGE_SUFFIX = "-exchange";

  private Map<String, String> queues;

  public Map<String, String> getQueues() {
    return queues;
  }

  public void setQueues(Map<String, String> queues) {
    this.queues = queues;
  }

  @Bean
  public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  public TopicExchange getExchange(String exchangeName) {
    return new TopicExchange(exchangeName, true, false);
  }

  public TopicExchange getDeadLetterExchange(String deadLetterExchangeName) {
    return new TopicExchange(deadLetterExchangeName, true, false);
  }

  public TopicExchange getRetryExchange(String retryExchangeName) {
    return new TopicExchange(retryExchangeName, true, false);
  }

  private Queue createQueue(final String queueName, String deadLetterExchangeName) {

    return QueueBuilder.durable(queueName)
        .withArgument("x-dead-letter-exchange", deadLetterExchangeName)
        .build();
  }

  private Queue createRetryQueue(final String queueName, String exchange) {

    return QueueBuilder.durable(queueName)
        .withArgument("x-dead-letter-exchange", exchange)
        .withArgument("x-message-ttl", 60000)
        .build();
  }

  private Queue createDeadLetterQueue(final String queueName) {
    return QueueBuilder.durable(queueName).build();
  }

  @Bean
  public Declarables declarableBeans() {
    final List<Declarable> declarables = new ArrayList<>();
    for (String queueKey : getQueues().keySet()) {
      final String exchangeName = getQueues().get(queueKey) + EXCHANGE_SUFFIX;
      final String retryExchangeName = exchangeName + RETRY_SUFFIX;
      final String deadLetterExchangeName = exchangeName + DEADLETTER_SUFFIX;
      final TopicExchange queueExchange = getExchange(exchangeName);
      final TopicExchange queueRetryExchange = getRetryExchange(retryExchangeName);
      final TopicExchange queueDeadLetterExchange = getDeadLetterExchange(deadLetterExchangeName);

      declarables.add(queueExchange);
      declarables.add(queueRetryExchange);
      declarables.add(queueDeadLetterExchange);

      String queueName = getQueues().get(queueKey);
      String retryName = queueName + RETRY_SUFFIX;
      String deadLetterName = queueName + DEADLETTER_SUFFIX;

      final Queue queue = createQueue(queueName, deadLetterExchangeName);
      final Queue retryQueue = createRetryQueue(retryName, exchangeName);
      final Queue deadLetterQueue = createDeadLetterQueue(deadLetterName);

      final Binding queueBinding = BindingBuilder.bind(queue).to(queueExchange).with(queueKey);

      final Binding retryBinding =
          BindingBuilder.bind(retryQueue).to(queueRetryExchange).with(queueKey);

      final Binding deadLetterBinding =
          BindingBuilder.bind(deadLetterQueue).to(queueDeadLetterExchange).with(queueKey);

      declarables.add(queue);
      declarables.add(queueBinding);
      declarables.add(retryQueue);
      declarables.add(retryBinding);
      declarables.add(deadLetterQueue);
      declarables.add(deadLetterBinding);
    }

    return new Declarables(declarables);
  }
}
