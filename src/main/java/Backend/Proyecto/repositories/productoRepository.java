package Backend.Proyecto.repositories;

import Backend.Proyecto.models.producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface productoRepository extends JpaRepository<producto, Integer> {
    Optional<producto> findProductoByNombre(String nombre);

    List<producto> findAll();

    List<producto> findByCategoria(String categoria);

}
