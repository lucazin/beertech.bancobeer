package br.com.beertech.fusion.repository;


import br.com.beertech.fusion.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByUsername(String username);
	
	Optional<Users> findByCnpj(String cnpj);
	
	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}


