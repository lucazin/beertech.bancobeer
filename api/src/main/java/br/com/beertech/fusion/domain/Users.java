package br.com.beertech.fusion.domain;

import br.com.beertech.fusion.domain.security.roles.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicUpdate
@Table(	name = "usuarios",
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email")
		})
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 50)
	private String nome;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	@JsonIgnore
	private String password;

	@NotBlank
	@Size(max = 11)
	@Email
	private String phonenumber;

	@NotBlank
	@Size(max = 120)
	private String cnpj;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "usuarios_roles",
				joinColumns = @JoinColumn(name = "usuariosId"),
				inverseJoinColumns = @JoinColumn(name = "roleId"))
	private Set<Role> roles = new HashSet<>();


	public Users() {}

	public Users(String username, String email, String password,String cnpj,String nome,String phonenumber) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.cnpj = cnpj;
		this.nome = nome;
		this.phonenumber = phonenumber;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getCnpj() { return cnpj; }

	public void setCnpj(String cnpj) { this.cnpj = cnpj; }

	public String getNome() { return nome; }

	public void setNome(String nome) { this.nome = nome; }

	public String getPhonenumber() { return phonenumber; }

	public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }
}
