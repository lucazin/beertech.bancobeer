package br.com.beertech.fusion.service;

import br.com.beertech.fusion.domain.Operation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;

@Service
@org.springframework.amqp.rabbit.annotation.RabbitListener(queues = "${javainuse.rabbitmq.queue}")
public class OperationListener implements MessageListener {

	public void onMessage(Message message) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = new String(message.getBody(), StandardCharsets.UTF_8);
			Operation transaction_pojo = objectMapper.readValue(json, Operation.class);

			if (new Validator(transaction_pojo).ValidateResponseRMQ()) {
				new RestClient(transaction_pojo).sendPostOperationAPI();
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}