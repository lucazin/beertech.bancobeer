package br.com.beertech.fusion.repository;

import br.com.beertech.fusion.domain.CurrentAccountDocument;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentAccountRepository extends MongoRepository<CurrentAccountDocument, Long> {

	CurrentAccountDocument findAccountByHash(String hash);

}
