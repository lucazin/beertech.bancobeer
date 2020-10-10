package br.com.beertech.fusion.service;

import br.com.beertech.fusion.domain.Beer;

import java.util.List;

public interface BeerService {

    Beer saveOrUpdate(Beer beer);

    void delete(Long idBeer);

    List<Beer> listAll();


}
