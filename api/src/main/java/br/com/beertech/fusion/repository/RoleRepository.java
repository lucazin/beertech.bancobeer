package br.com.beertech.fusion.repository;


import br.com.beertech.fusion.domain.security.roles.EnumRole;
import br.com.beertech.fusion.domain.security.roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, Long> {
	Optional<Role> findByName(EnumRole name);
}
