package Backend.Proyecto.repositories;

import Backend.Proyecto.models.carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface carritoRepository extends JpaRepository<carrito, Integer> {
    List<carrito> findAll();
}
