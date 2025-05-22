package Backend.Proyecto.repositories;

import Backend.Proyecto.models.itemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface itemPedidoRepository extends JpaRepository<itemPedido, Integer> {
    List<itemPedido> findAll();
}
