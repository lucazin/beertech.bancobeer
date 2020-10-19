package br.com.beertech.fusion.repository;

import java.util.List;

import br.com.beertech.fusion.domain.collections.OperationDocument;
import org.springframework.data.jpa.repository.Query;

import br.com.beertech.fusion.domain.Operation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OperationRepository extends MongoRepository<OperationDocument, Long> {
	
	List<OperationDocument> findAllByHash(String hash);

}
