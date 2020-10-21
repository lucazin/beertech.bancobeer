package br.com.beertech.fusion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.beertech.fusion.controller.dto.CurrentAccountUserDTO;
import br.com.beertech.fusion.domain.CurrentAccount;

public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, Long> {

	CurrentAccount findAccountByHash(String hash);
	
	Optional<CurrentAccount> findAccountByCnpj(String cnpj);

    @Query(value = "select * from conta_corrente c where c.id and c.hash = ?1", nativeQuery = true)
	CurrentAccount findAccountByIdAndHash(Long id, String hash);
	
}
