package br.com.beertech.fusion.service.impl;

import br.com.beertech.fusion.domain.Operacao;
import br.com.beertech.fusion.repository.OperationRepository;
import br.com.beertech.fusion.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationServiceImpl implements OperationService {

    private OperationRepository operationRepository;

    @Autowired
    public OperationServiceImpl(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public Operacao NovaTransacao(Operacao operacao) {
        return operationRepository.save(operacao);
    }
    @Override
    public List<Operacao> ListaTransacoes() {
        return operationRepository.findAll();
    }

    @Override
    public void RemoveTransacao(Long idOperation) {
        operationRepository.delete(getOperationById(idOperation));
    }


    private Operacao getOperationById(Long idOperation) {
        return operationRepository.getOne(idOperation);
    }

}
