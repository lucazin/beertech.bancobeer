package br.com.beertech.fusion.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.beertech.fusion.controller.dto.CurrentAccountUserDTO;

public class CurrentAccountUserRowMapper  implements RowMapper<CurrentAccountUserDTO> {

	@Override
	public CurrentAccountUserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

		CurrentAccountUserDTO currentAccountUserDTO = new CurrentAccountUserDTO();

		currentAccountUserDTO.setHash(rs.getString("hash"));
		currentAccountUserDTO.setCnpj(rs.getString("cnpj"));
		currentAccountUserDTO.setEmail(rs.getString("email"));
		currentAccountUserDTO.setNome(rs.getString("nome"));
		currentAccountUserDTO.setNome(rs.getString("phonenumber"));
		
		return currentAccountUserDTO;
	}

}
