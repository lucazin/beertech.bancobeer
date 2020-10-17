package br.com.beertech.fusion.repository;
import br.com.beertech.fusion.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}