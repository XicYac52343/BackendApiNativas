package Backend.Proyecto.controllers;

import Backend.Proyecto.models.producto;
import Backend.Proyecto.services.productoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path= "api/productos")
@CrossOrigin(origins = "*")

public class productoController {
    private final productoService productoServicio;

    @Autowired
    public productoController(productoService productoServicio) {
        this.productoServicio = productoServicio;
    }

    @GetMapping
    public List<producto> getProductos(@RequestParam(name="categoria", required=false) String categoria) {
        if(categoria == null) {
            return this.productoServicio.getProductos();
        }else{
            return this.productoServicio.getProductosByCategoria(categoria);
        }
    }

    @GetMapping(path="homeAndroid")
    public List<producto> getProductosHomeAndroid() {
        return this.productoServicio.getProductosHomeAndroid();
    }

    @GetMapping(path="productoID")
    public ResponseEntity<Object> getProducto(@RequestParam(name="id", required=true) Integer productoID) {
        return this.productoServicio.getProducto(productoID);
    }

    @GetMapping(path="productosFavoritos")
    public List<producto> getProductosFavoritosByUsuario(@RequestParam(name="id", required=true) Integer id) {
        return this.productoServicio.getProductosFavoritosByUsuario(id);
    }


    @PostMapping
    public ResponseEntity<Object> registrarProducto(@RequestBody producto producto) {
        return this.productoServicio.nuevoProducto(producto);
    }

    @PutMapping
    public ResponseEntity<Object> actualizarProducto(@RequestBody producto producto) {
        return this.productoServicio.nuevoProducto(producto);
    }

    @DeleteMapping(path="{productoID}")
    public ResponseEntity<Object> eliminarProducto(@PathVariable("productoID") Integer id) {
        return this.productoServicio.borrarProducto(id);
    }
}
