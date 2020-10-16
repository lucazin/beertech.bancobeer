package br.com.beertech.fusion.repository;
import br.com.beertech.fusion.domain.security.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuarios, Integer> {
    Usuarios findByUsername(String username);
}