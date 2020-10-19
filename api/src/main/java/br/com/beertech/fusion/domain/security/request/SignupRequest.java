package br.com.beertech.fusion.domain.security.request;

import javax.validation.constraints.*;
import java.util.Set;

public class SignupRequest {

    private String username;

    private String email;
    
    private Set<String> role;

    private String password;

    private String nome;

    private String cnpj;

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
    
    public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
    }

    public String getCnpj() { return cnpj; }

    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }
}
