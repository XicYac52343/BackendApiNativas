package Backend.Proyecto.repositories;

import Backend.Proyecto.models.usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface usuarioRepository extends JpaRepository<usuario, Integer> {
    List<usuario> findAll();
}
