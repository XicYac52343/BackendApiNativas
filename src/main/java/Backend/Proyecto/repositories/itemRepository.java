package Backend.Proyecto.repositories;

import Backend.Proyecto.models.item;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface itemRepository extends JpaRepository<item, Integer> {
    List<item> findAll();
}
