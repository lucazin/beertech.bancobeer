package br.com.beertech.fusion.repository;


import br.com.beertech.fusion.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByUsernameAndAccountType(String username, Integer AccountType);

	Boolean existsByEmailAndAccountType(String email, Integer AccountType);

	Boolean existsByCnpjAndAccountType(String cnpj, Integer AccountType);

}
