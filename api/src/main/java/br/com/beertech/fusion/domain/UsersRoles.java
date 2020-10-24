package br.com.beertech.fusion.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

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
