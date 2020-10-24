package br.com.beertech.fusion.domain.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.beertech.fusion.domain.security.roles.Role;

@Entity
@DynamicUpdate
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 50)
    @Column(nullable = false)
	private String name;

	@NotBlank
	@Size(max = 20)
    @Column(nullable = false, unique = true)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
    @Column(nullable = false, unique = true)
	private String email;

	@NotBlank
	@Size(max = 120)
	@JsonIgnore
    @Column(nullable = false)
	private String password;

	@NotBlank
	@Size(max = 11)
	@Email
	private String phonenumber;

	@NotBlank
	@Size(max = 120)
    @Column(nullable = false, unique = true)
	private String cnpj;

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
	private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<CurrentAccount> currentAccounts = new ArrayList<>();

	public User() {}

	public User(String username, String email, String password,String cnpj,String name) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.cnpj = cnpj;
		this.name = name;
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

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public String getPhonenumber() { return phonenumber; }

	public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }

    public List<CurrentAccount> getCurrentAccounts() {
        return currentAccounts;
    }

    public void setCurrentAccounts(List<CurrentAccount> currentAccounts) {
        this.currentAccounts = currentAccounts;
    }
}
