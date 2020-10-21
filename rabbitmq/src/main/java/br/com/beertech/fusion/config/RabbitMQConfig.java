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
	Queue queueOperation() {
		return new Queue(queueOperationName, false);
	}

	@Bean
	Queue queueTransfer() {
		return new Queue(queueTransferName, false);
	}

			
	@Bean
	MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory ) {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
		simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
		simpleMessageListenerContainer.setQueues(queueOperation());
		simpleMessageListenerContainer.setMessageListener(new OperationListener());
		return simpleMessageListenerContainer;

	}
	
	@Bean
	MessageListenerContainer messageListenerContainerTransfer(ConnectionFactory connectionFactory ) {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
		simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
		simpleMessageListenerContainer.setQueues(queueTransfer());
		simpleMessageListenerContainer.setMessageListener(new TransferListener());
		return simpleMessageListenerContainer;

	}
}
