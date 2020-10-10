package br.com.beertech.fusion.service;

import br.com.beertech.fusion.domain.operacao;

import java.util.List;

public interface OperationService {

    operacao NovaTransacao(operacao operacao);

    void RemoveTransacao(Long idBeer);

    List<operacao> ListaTransacoes();


}
