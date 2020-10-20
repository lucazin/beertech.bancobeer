package br.com.beertech.fusion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.beertech.fusion.domain.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long> {

	List<Operation> findAllByOperationHash(String hashOperation);

	List<Operation> findAllByOperationAgencyAndOperationAccountNumber(String agency,String accountnumber);

	List<Operation> findAllByOperationHashAndOperationType(String hashOperation,int operationType);

}
