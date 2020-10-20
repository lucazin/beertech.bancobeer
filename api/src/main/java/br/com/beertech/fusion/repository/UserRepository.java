package br.com.beertech.fusion.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.beertech.fusion.domain.UserDocument;

@Repository
public interface UserRepository extends MongoRepository<UserDocument, String> {
}
