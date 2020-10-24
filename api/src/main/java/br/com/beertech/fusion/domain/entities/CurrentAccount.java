package br.com.beertech.fusion.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "current_account")
public class CurrentAccount implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Long id;

  @Column(nullable = false, unique = true)
  private String hash;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_fk", nullable = false)
  private User user;

  @OneToMany(mappedBy = "currentAccount")
  private List<Operation> operations = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
      this.id = id;
  }

  public String getHash() {
    return hash;
  }

  public void setHash(String hash) {
    this.hash = hash;
  }

  public User getUser() {
      return user;
  }

  public void setUser(User user) {
      this.user = user;
  }
}
