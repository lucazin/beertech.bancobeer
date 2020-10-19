package br.com.beertech.fusion.repository;

import br.com.beertech.fusion.domain.collections.CurrentAccountDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.beertech.fusion.domain.CurrentAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CurrentAccountRepository extends MongoRepository<CurrentAccountDocument, Long> {

	CurrentAccountDocument findAccountByHash(String hash);

}
