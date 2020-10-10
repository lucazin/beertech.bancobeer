package br.com.beertech.fusion.domain.builder;

import br.com.beertech.fusion.domain.Beer;

import java.math.BigDecimal;

public final class BeerBuilder implements Builder<Beer> {

  private Beer beer = new Beer();

    private BeerBuilder() {
    }

    public static BeerBuilder aBeer() {
        return new BeerBuilder();
    }

    public BeerBuilder withIdBeer(Long idBeer) {
        this.beer.setIdBeer(idBeer);
        return this;
    }

    public BeerBuilder withName(String name) {
        this.beer.setName(name);
        return this;
    }

    public BeerBuilder withPrice(BigDecimal price) {
        this.beer.setPrice(price);
        return this;
    }

    @Override
    public Beer builder() {
        return this.beer;
    }



}
