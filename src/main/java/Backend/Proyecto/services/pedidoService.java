package Backend.Proyecto.services;


import Backend.Proyecto.models.pedido;
import Backend.Proyecto.models.usuario;
import Backend.Proyecto.repositories.pedidoRepository;
import Backend.Proyecto.repositories.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class pedidoService {
    private final pedidoRepository pedidoRepository;
    private final usuarioRepository usuarioRepository;
    HashMap<String, Object> datos;


    @Autowired
    public pedidoService(pedidoRepository pedidoRepository, Backend.Proyecto.repositories.usuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<pedido> getPedidos(){
        return pedidoRepository.findAll();
    }

    public ResponseEntity<Object> getPedidoById(Integer id){
        Optional<pedido> pedido = pedidoRepository.findById(id);
        if(pedido.isPresent()){
            return ResponseEntity.ok(pedido.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public List<pedido> getPedidoUsuario(Integer usuarioID){
        Optional<usuario> res = this.usuarioRepository.findById(usuarioID);
        if(res.isPresent()){
            usuario usuario = res.get();
            return usuario.getPedidos();
        }else{
            return null;
        }
    }

    public ResponseEntity<Object> crearPedido(pedido pedido){
        this.pedidoRepository.save(pedido);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> editarPedido(pedido pedido){
        Optional<pedido> res = this.pedidoRepository.findById(pedido.getId());
        datos = new HashMap<>();

        if(res.isPresent()){
            datos.put("message", "Pedido encontrado");
            this.pedidoRepository.save(pedido);
            return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
        }else{
            datos.put("Error", true);
            datos.put("message", "Pedido no encontrado");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> eliminarPedido(Integer id){
        datos = new HashMap<>();
        boolean existe = this.pedidoRepository.existsById(id);
        if(existe){
            pedidoRepository.deleteById(id);
            datos.put("message", "Pedido Eliminado");
            return new ResponseEntity<>(datos, HttpStatus.OK);
        }else{
            datos.put("Error", true);
            datos.put("message", "No existe un Pedido con ese id.");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
    }
}
