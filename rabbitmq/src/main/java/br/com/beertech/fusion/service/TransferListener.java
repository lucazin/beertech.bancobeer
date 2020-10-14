package br.com.beertech.fusion.service;

import java.nio.charset.StandardCharsets;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.beertech.fusion.domain.Transfer;

@Service
@org.springframework.amqp.rabbit.annotation.RabbitListener(queues = "${javainuse.rabbitmq.queue.transfer}")
public class TransferListener implements MessageListener {

	public void onMessage(Message message) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = new String(message.getBody(), StandardCharsets.UTF_8);
			System.out.println(json);
			Transfer transaction_pojo = objectMapper.readValue(json, Transfer.class);

			if (new Validator(transaction_pojo).ValidateTransferResponseRMQ()) {
				new RestClient(transaction_pojo).sendPostTransferAPI();
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
