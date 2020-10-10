package br.com.beertech.fusion.repository;

import br.com.beertech.fusion.domain.operacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<operacao, Long> {

}
