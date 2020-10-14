package br.com.beertech.fusion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.beertech.fusion.domain.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long> {

	@Query(value = "select * from operacao o where o.hash = ?1", nativeQuery=true)
	List<Operation> listTransactionByHash(String hash);

}
