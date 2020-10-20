package br.com.beertech.fusion.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.controller.dto.TransferDTO;
import br.com.beertech.fusion.service.PublishTransaction;

@Service
public class PublishTransactionImpl implements PublishTransaction {
	
	private static final String HOST = "localhost";
	private static final String QUEUE_OPERATION_NAME = "queueDeposit";
	private static final String QUEUE_TRANSFER_NAME = "queueTransfer";
	
	@Override
	public void publisheOperation(OperationDTO operationDTO) {
		ObjectMapper objectMapper = new ObjectMapper();

		String msgm = null;

		try {
			msgm = objectMapper.writeValueAsString(operationDTO);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);

		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
			channel.queueDeclare(QUEUE_OPERATION_NAME, false, false, false, null);
			channel.basicPublish("", QUEUE_OPERATION_NAME, null, msgm.getBytes(StandardCharsets.UTF_8));
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void publisheTransfer(TransferDTO transferDTO) {
	
		ObjectMapper objectMapper = new ObjectMapper();

		String msgm = null;

		try {
			msgm = objectMapper.writeValueAsString(transferDTO);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);

		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel())
		{
			channel.queueDeclare(QUEUE_TRANSFER_NAME, false, false, false, null);
			channel.basicPublish("", QUEUE_TRANSFER_NAME, null, msgm.getBytes(StandardCharsets.UTF_8));
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
		
	}

}
