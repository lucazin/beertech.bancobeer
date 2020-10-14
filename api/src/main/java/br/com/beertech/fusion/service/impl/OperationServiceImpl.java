package br.com.beertech.fusion.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import br.com.beertech.fusion.controller.dto.OperationDTO;
import br.com.beertech.fusion.controller.dto.TransferDTO;
import br.com.beertech.fusion.domain.Balance;
import br.com.beertech.fusion.domain.CurrentAccount;
import br.com.beertech.fusion.domain.Operation;
import br.com.beertech.fusion.domain.OperationType;
import br.com.beertech.fusion.exception.FusionException;
import br.com.beertech.fusion.repository.OperationRepository;
import br.com.beertech.fusion.service.CurrentAccountService;
import br.com.beertech.fusion.service.OperationService;
import br.com.beertech.fusion.service.SaldoService;

@Service
public class OperationServiceImpl implements OperationService {

	private static final String HOST = "localhost";
	private static final String QUEUE_OPERATION_NAME = "queueFusion";
	private static final String QUEUE_TRANSFER_NAME = "queueTransfer";

	private OperationRepository operationRepository;

	@Autowired
	private SaldoService saldoService;

	@Autowired
	private CurrentAccountService currentAccountService;

	@Autowired
	public OperationServiceImpl(OperationRepository operationRepository) {
		this.operationRepository = operationRepository;
	}

	@Override
	public Operation newTransaction(Operation operation) {
		return operationRepository.save(operation);
	}

	@Override
	public List<Operation> ListaTransacoes() {
		return operationRepository.findAll();
	}

	@Override
	public void RemoveTransacao(Long idOperation) {
		operationRepository.delete(getOperationById(idOperation));
	}

	private Operation getOperationById(Long idOperation) {
		return operationRepository.getOne(idOperation);
	}

	@Override
	public List<Operation> listTransactionByHash(String hash) {
		return operationRepository.listTransactionByHash(hash);
	}

	@Override
	public TransferDTO saveTransfer(TransferDTO transferDTO) throws FusionException {

		CurrentAccount accountOrigin = currentAccountService.findByHash(transferDTO.getHashOrigin());
		CurrentAccount accountDestiny = currentAccountService.findByHash(transferDTO.getHashDestination());

		if (accountOrigin == null) {
			throw new FusionException("Conta de origem inexistente!");
		}
		if (accountDestiny == null) {
			throw new FusionException("Conta de destino inexistente!");
		}

		Balance sldOrigin = calculateBalance(transferDTO.getHashOrigin());

		if (sldOrigin.getSaldo() != null && sldOrigin.getSaldo() >= transferDTO.getValue()) {
			Operation origin = new Operation(transferDTO, OperationType.SAQUE, transferDTO.getHashOrigin());
			Operation destiny = new Operation(transferDTO, OperationType.DEPOSITO, transferDTO.getHashDestination());
			operationRepository.save(origin);
			operationRepository.save(destiny);
		} else {
			throw new FusionException("Saldo insuficiente!");
		}
		return transferDTO;
	}

	@Override
	public Balance calculateBalance(String hash) {

		Balance saldo = new Balance(null);

		try {
			List<Operation> transacoes = listTransactionByHash(hash);
			if (transacoes.isEmpty()) {
				return saldo;
			}
			saldo = saldoService
					.calcularSaldo(transacoes.stream().map(Operation::getOperacaoDto).collect(Collectors.toList()));
			return saldo;
		} catch (Exception e) {
			throw e;
		}
	}

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

		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
			channel.queueDeclare(QUEUE_TRANSFER_NAME, false, false, false, null);
			channel.basicPublish("", QUEUE_TRANSFER_NAME, null, msgm.getBytes(StandardCharsets.UTF_8));
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
		
	}

}
