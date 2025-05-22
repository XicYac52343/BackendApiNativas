package Backend.Proyecto.services;

import Backend.Proyecto.models.itemPedido;
import Backend.Proyecto.models.pedido;
import Backend.Proyecto.repositories.itemPedidoRepository;
import Backend.Proyecto.repositories.pedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class itemPedidoService {
    HashMap<String, Object> datos;


    private final itemPedidoRepository itemPedidoRepository;
    private final pedidoRepository pedidoRepository;

    @Autowired
    public itemPedidoService(itemPedidoRepository itemPedidoRepository, pedidoRepository pedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public List<itemPedido> getItems() {
        return this.itemPedidoRepository.findAll();
    }

    public List<itemPedido> getItemsPedido(int idPedido) {
        Optional<pedido> resPedido = this.pedidoRepository.findById(idPedido);
        if (resPedido.isPresent()) {
            pedido pedido = resPedido.get();
            return pedido.getListaItems();
        } else {
            return null;
        }
    }

    public ResponseEntity<Object> getItem(int id) {
        datos = new HashMap<>();
        Optional<itemPedido> res = this.itemPedidoRepository.findById(id);
        if (res.isPresent()) {
            return ResponseEntity.ok().body(res.get());
        } else {
            datos.put("Error", true);
            datos.put("message", "Item no encontrado");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> crearItem(itemPedido itemPedido, Integer pedidoID) {
        datos = new HashMap<>();
        if (itemPedido.getId() == null) {
            Optional<pedido> resPedido = this.pedidoRepository.findById(pedidoID);
            if (resPedido.isPresent()) {
                pedido pedido = resPedido.get();
                itemPedido.setPedido(pedido);
                pedido.getListaItems().add(itemPedido);

                this.pedidoRepository.save(pedido);

                this.itemPedidoRepository.save(itemPedido);
                datos.put("message", "Item creado");
                return new ResponseEntity<>(datos, HttpStatus.CREATED);
            } else {
                datos.put("Error", true);
                datos.put("message", "Pedido no encontrado");
                return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
            }
        } else {
            datos.put("Error", true);
            datos.put("message", "Item ya existente");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<Object> editarItem(itemPedido itemPedido) {
        datos = new HashMap<>();
        Optional<itemPedido> res = this.itemPedidoRepository.findById(itemPedido.getId());
        if (res.isPresent() && itemPedido.getId()!=null) {
            itemPedidoRepository.save(itemPedido);
            datos.put("message", "Item editado");
            return new ResponseEntity<>(datos, HttpStatus.OK);
        }else{
            datos.put("Error", true);
            datos.put("message", "Item no encontrado");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> eliminarItem(int id) {
        datos = new HashMap<>();
        Optional<itemPedido> res = this.itemPedidoRepository.findById(id);
        if (res.isPresent()) {
            this.itemPedidoRepository.deleteById(id);
            datos.put("message", "Item eliminado");
            return new ResponseEntity<>(datos, HttpStatus.OK);
        }else{
            datos.put("Error", true);
            datos.put("message", "Item no encontrado");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }
    }
}
