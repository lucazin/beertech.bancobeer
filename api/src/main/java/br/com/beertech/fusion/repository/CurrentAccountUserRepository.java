package br.com.beertech.fusion.repository;

import java.util.List;

import br.com.beertech.fusion.controller.dto.CurrentAccountUserDTO;

public interface CurrentAccountUserRepository {
	
	public List<CurrentAccountUserDTO> findAccountAllUser();

	public List<CurrentAccountUserDTO> findAccountByUser(String user);
}
