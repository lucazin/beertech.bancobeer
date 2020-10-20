package br.com.beertech.fusion.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.beertech.fusion.domain.CurrentAccountDocument;

@Repository
public interface CurrentAccountRepository extends MongoRepository<CurrentAccountDocument, String> {

	CurrentAccountDocument findAccountByHash(String hash);

}
