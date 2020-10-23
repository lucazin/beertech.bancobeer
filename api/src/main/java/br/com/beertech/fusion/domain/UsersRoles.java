package br.com.beertech.fusion.domain;

import br.com.beertech.fusion.domain.security.roles.Role;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicUpdate
@Table(	name = "usuarios_roles")
public class UsersRoles {

	@Id
	private Long usuariosId;
	private int roleId;

	public UsersRoles() {}

	public UsersRoles(Long usuariosId, int roleId) {
		this.usuariosId = usuariosId;
		this.roleId = roleId;
	}

	public Long getUsuariosId() {
		return usuariosId;
	}

	public void setUsuariosId(Long usuariosId) {
		this.usuariosId = usuariosId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}


}
