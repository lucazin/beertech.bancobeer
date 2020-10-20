package br.com.beertech.fusion.repository;


import br.com.beertech.fusion.domain.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserDocument, Long> {
}
