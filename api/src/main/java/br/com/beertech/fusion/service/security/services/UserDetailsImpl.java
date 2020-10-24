package br.com.beertech.fusion.service.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.beertech.fusion.domain.entities.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	private String name;

	private String email;
	
	private  String cnpj;

	@JsonIgnore
	private String password;

	private  String phonenumber;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String username, String email, String password, String cnpj,
			Collection<? extends GrantedAuthority> authorities,String name,String phonenumber) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.cnpj = cnpj;
		this.name = name;
		this.phonenumber = phonenumber;
	}

	public static UserDetailsImpl build(User usuario) {
		List<GrantedAuthority> authorities = usuario.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				usuario.getId(),
				usuario.getUsername(),
				usuario.getEmail(),
				usuario.getPassword(),
				usuario.getCnpj(),
				authorities,
				usuario.getName());
				usuario.getPhonenumber());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	public String getCnpj() { return cnpj; }


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public String getPhonenumber() { return phonenumber; }

	public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }
}
