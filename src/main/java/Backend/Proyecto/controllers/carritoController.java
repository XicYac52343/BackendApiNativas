package Backend.Proyecto.controllers;

import Backend.Proyecto.models.carrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import Backend.Proyecto.services.carritoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/carrito")
@CrossOrigin(origins = "*")

public class carritoController {
    private final carritoService carritoService;

    @Autowired
    public carritoController(carritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping
   public List<carrito> getCarritos(){
        return this.carritoService.getCarritos();
    }

    @GetMapping(path="{carritoID}")
    public ResponseEntity<Object> getCarrito(@PathVariable("carritoID") Integer id){
        return this.carritoService.getCarrito(id);
    }

    @GetMapping(path="usuarioID")
    public ResponseEntity<Object> getCarritoByUser(@RequestParam(name="id", required=true) Integer idUsuario){
        return this.carritoService.getCarritoByUser(idUsuario);
    }

    @PostMapping
    public ResponseEntity<Object> crearCarrito(@RequestBody carrito carrito){
        return this.carritoService.nuevoCarrito(carrito);
    }

    @PutMapping
    public ResponseEntity<Object> editarCarrito(@RequestBody carrito carrito){
        return this.carritoService.editarCarrito(carrito);
    }

    @DeleteMapping(path="{carritoID}")
    public ResponseEntity<Object> eliminarCarrito(@PathVariable("carritoID") Integer id){
        return this.carritoService.eliminarCarrito(id);
    }

}
