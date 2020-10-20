package br.com.beertech.fusion.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "userstest")
public class UserDocument {

    @Id
    private String id;

    private String nome;

    private String username;

    private String email;

    private String password;

    private String cnpj;

    private List<String> roles;

    public UserDocument() {}

    public UserDocument(String username, String email, String password,String cnpj,String nome) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.cnpj = cnpj;
        this.nome = nome;
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
