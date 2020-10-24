package br.com.beertech.fusion.config;

import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.validation.SmartValidator;

@Configuration
public class CustomRabbitListenerConfigurer implements RabbitListenerConfigurer {

  @Bean
  public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
    return new DefaultMessageHandlerMethodFactory();
  }

  @Override
  public void configureRabbitListeners(
      final RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
    rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(
        this.messageHandlerMethodFactory());
  }
}
