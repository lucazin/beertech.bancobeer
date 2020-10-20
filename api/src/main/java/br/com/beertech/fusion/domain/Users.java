package br.com.beertech.fusion.domain;

import br.com.beertech.fusion.domain.security.roles.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(	name = "usuarios")

//@Table(	name = "usuarios",
//		uniqueConstraints = {
//			@UniqueConstraint(columnNames = "username"),
//			@UniqueConstraint(columnNames = "email")
//		})
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 50)
	private String name;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@NotBlank
	@Size(max = 14)
	private String cnpj;

	@NotBlank
	@Size(max = 14)
	private String phone;

	@NotBlank
	private int accountType;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();


	@ManyToMany(fetch = FetchType.LAZY,cascade=CascadeType.PERSIST)
	@JoinTable(	name = "user_accounts",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "account_id"))
	private Set<CurrentAccount> Accounts = new HashSet<>();

	public Users() {}

	public Users(String username, String email, String password,String cnpj,String name,String phone) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.cnpj = cnpj;
		this.name = name;
		this.phone = phone;
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

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public String getPhone() { return phone; }

	public void setPhone(String phone) { this.phone = phone; }

	public Set<CurrentAccount> getAccounts() { return Accounts; }

	public void setAccounts(Set<CurrentAccount> accounts) { Accounts = accounts; }

	public int getAccountType() { return accountType; }

	public void setAccountType(int accountType) { this.accountType = accountType; }
}
