package br.com.beertech.fusion.service;

import br.com.beertech.fusion.domain.Operacao;

import java.util.List;

public interface OperationService {

    Operacao NovaTransacao(Operacao operacao);

    void RemoveTransacao(Long idBeer);

    List<Operacao> ListaTransacoes();


}
