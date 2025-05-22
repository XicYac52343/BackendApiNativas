package Backend.Proyecto.repositories;

import Backend.Proyecto.models.pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface pedidoRepository extends JpaRepository<pedido, Integer> {
    List<pedido> findAll();
}
