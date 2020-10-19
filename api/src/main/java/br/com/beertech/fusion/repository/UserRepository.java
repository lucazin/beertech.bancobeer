package br.com.beertech.fusion.repository;


import br.com.beertech.fusion.domain.collections.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserDocument, Long> {

	Optional<UserDocument> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);


}
