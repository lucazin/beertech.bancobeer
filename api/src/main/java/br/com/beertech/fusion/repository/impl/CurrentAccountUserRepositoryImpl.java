package br.com.beertech.fusion.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.beertech.fusion.controller.dto.CurrentAccountUserDTO;
import br.com.beertech.fusion.repository.CurrentAccountUserRepository;
import br.com.beertech.fusion.rowmapper.CurrentAccountUserRowMapper;

@Repository
public class CurrentAccountUserRepositoryImpl implements CurrentAccountUserRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<CurrentAccountUserDTO> findAccountAllUser() {
		
		String sql	 = "select u.cnpj, u.nome, u.email, c.hash from conta_corrente c, usuarios u where c.cnpj = u.cnpj";
		return jdbcTemplate.query(sql, new CurrentAccountUserRowMapper());
	}

	public List<CurrentAccountUserDTO> findAccountByUser(String user) {
		
		String sql	 = "select c.hash from conta_corrente c, usuarios u where c.cnpj = u.cnpj and u.username = "+ user;
		return jdbcTemplate.query(sql, new CurrentAccountUserRowMapper());
	}

	
}
