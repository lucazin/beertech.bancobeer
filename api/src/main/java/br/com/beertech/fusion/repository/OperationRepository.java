package br.com.beertech.fusion.repository;

import br.com.beertech.fusion.domain.Operacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operacao, Long> {

}
