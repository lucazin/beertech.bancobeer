package br.com.beertech.fusion.repository;

import br.com.beertech.fusion.domain.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<Beer, Long> {

}
