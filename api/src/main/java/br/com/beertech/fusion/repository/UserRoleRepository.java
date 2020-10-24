package br.com.beertech.fusion.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.beertech.fusion.domain.UsersRoles;


@Repository
public interface UserRoleRepository extends JpaRepository<UsersRoles, Long> {
	UsersRoles findUsersRolesByUsuariosId(Long idUser);
}
