package br.com.beertech.fusion.controller.dto;

import javax.validation.constraints.NotBlank;

public class CurrentAccountDTO {

  @NotBlank
  private String cnpj;

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }
}
