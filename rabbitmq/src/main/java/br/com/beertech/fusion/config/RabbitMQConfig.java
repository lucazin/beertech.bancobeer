package br.com.beertech.fusion.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.beertech.fusion.service.OperationListener;
import br.com.beertech.fusion.service.TransferListener;

@Configuration
public class RabbitMQConfig {

	@Value("${spring.rabbitmq.queues.operation}")
	String queueOperationName;

	@Value("${spring.rabbitmq.queues.transfer}")
	String queueTransferName;

	@Bean
	Queue queue(String queueName) {
		return new Queue(queueName, false);
	}

			
	@Bean
	MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory ) {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
		simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
		simpleMessageListenerContainer.setQueues(queue(queueOperationName));
		simpleMessageListenerContainer.setMessageListener(new OperationListener());
		return simpleMessageListenerContainer;

	}
	
	@Bean
	MessageListenerContainer messageListenerContainerTransfer(ConnectionFactory connectionFactory ) {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
		simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
		simpleMessageListenerContainer.setQueues(queue(queueTransferName));
		simpleMessageListenerContainer.setMessageListener(new TransferListener());
		return simpleMessageListenerContainer;

	}
}
