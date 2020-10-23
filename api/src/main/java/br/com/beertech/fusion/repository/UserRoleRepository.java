package br.com.beertech.fusion.repository;


import br.com.beertech.fusion.domain.UsersRoles;
import br.com.beertech.fusion.domain.security.roles.EnumRole;
import br.com.beertech.fusion.domain.security.roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UsersRoles, Long> {
	UsersRoles findUsersRolesByUsuariosId(Long idUser);
}
