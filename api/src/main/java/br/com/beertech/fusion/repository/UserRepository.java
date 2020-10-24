package br.com.beertech.fusion.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.beertech.fusion.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	Boolean existsByCnpj(String cnpj);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	List<User> findAllBy();

}


