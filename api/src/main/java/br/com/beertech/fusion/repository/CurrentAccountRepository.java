package br.com.beertech.fusion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.beertech.fusion.domain.CurrentAccount;

import java.util.List;

public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, Long>  {
	
	CurrentAccount findById(long id);

	CurrentAccount findByHash(String hash);

	Boolean existsBydocumentCustomerAndAccountType(String Document, Integer AccountType);

	CurrentAccount findByAgencyAndAccountNumber(String Agency,String accountnumber);

}
