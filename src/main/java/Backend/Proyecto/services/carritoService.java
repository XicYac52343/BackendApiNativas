package Backend.Proyecto.services;


import Backend.Proyecto.models.carrito;
import Backend.Proyecto.models.usuario;
import Backend.Proyecto.repositories.carritoRepository;
import Backend.Proyecto.repositories.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class carritoService {
    HashMap<String, Object> datos;

    private final carritoRepository carritoRepository;
    private final usuarioRepository usuarioRepository;

    @Autowired
    public carritoService(carritoRepository carritoRepository, Backend.Proyecto.repositories.usuarioRepository usuarioRepository) {
        this.carritoRepository = carritoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<carrito> getCarritos(){
        return this.carritoRepository.findAll();
    }

    public ResponseEntity<Object> getCarrito(int id){
        Optional<carrito> carrito = carritoRepository.findById(id);
        if(carrito.isPresent()){
            return ResponseEntity.ok().body(carrito.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Object> getCarritoByUser(int usuarioID){
        Optional<usuario> usuario = usuarioRepository.findById(usuarioID);
        if(usuario.isPresent()){
            return ResponseEntity.ok().body(usuario.get().getCarrito());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Object> nuevoCarrito(carrito carrito){
        datos = new HashMap<>();

        if(carrito != null){
            Optional<carrito> res = carritoRepository.findById(carrito.getId());
            if(res.isPresent()){
                datos.put("error", true);
                datos.put("message", "El item ya existe");
                return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
            }
        }
        carritoRepository.save(carrito);
        datos.put("message", "Se guardó con éxito");
        datos.put("data", carrito);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> nuevoCarritoUsuario(usuario usuario){
        carrito carrito = new carrito();
        datos = new HashMap<>();
        carrito.setUsuario(usuario);
        carritoRepository.save(carrito);
        datos.put("message", "Carrito creado para el usuario");
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> editarCarrito(carrito carrito){
        Optional<carrito> res = carritoRepository.findById(carrito.getId());
        if(res.isPresent() && carrito.getId() != 0){
            carritoRepository.save(carrito);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Object> eliminarCarrito(int id){
        datos = new HashMap<>();
        boolean existe = this.carritoRepository.existsById(id);
        if(existe){
            this.carritoRepository.deleteById(id);
            datos.put("message", "Carrito Eliminado");
            return new ResponseEntity<>(datos, HttpStatus.OK);
        }else{
            datos.put("Error", true);
            datos.put("message", "No existe un carrito con ese id.");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
    }
}
