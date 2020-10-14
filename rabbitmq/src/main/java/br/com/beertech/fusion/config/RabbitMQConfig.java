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

	@Value("${javainuse.rabbitmq.queue}")
	String queueName;

	@Value("${javainuse.rabbitmq.queue.transfer}")
	String queueTransfer;
	
	
	@Value("${spring.rabbitmq.username}")
	String username;

	@Value("${spring.rabbitmq.password}")
	private String password;

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}
	
	@Bean
	Queue queueTransfer() {
		return new Queue(queueTransfer, false);
	}			
			
	@Bean
	MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory ) {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
		simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
		simpleMessageListenerContainer.setQueues(queue());
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
