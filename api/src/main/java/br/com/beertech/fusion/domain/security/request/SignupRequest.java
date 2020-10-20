package br.com.beertech.fusion.domain.security.request;

import javax.validation.constraints.*;
import java.util.Set;

public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 14)
    private String cnpj;

    @NotBlank
    @Size(max = 14)
    private String phone;

    @NotBlank
    private int accountType;

    @NotBlank
    private double approvedLimit;

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


    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public int getAccountType() { return accountType; }

    public void setAccountType(int accountType) { this.accountType = accountType; }

    public double getApprovedLimit() { return approvedLimit; }

    public void setApprovedLimit(double approvedLimit) { this.approvedLimit = approvedLimit; }
}
