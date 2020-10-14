package br.com.beertech.fusion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.beertech.fusion.domain.CurrentAccount;

public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, Long>  {
	
	@Query(value = "select * from conta_corrente c where c.hash = ?1", nativeQuery=true)
	CurrentAccount findAccountByHash(String hash);

}
