package Backend.Proyecto.services;

import Backend.Proyecto.models.producto;
import Backend.Proyecto.repositories.productoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class productoService {
    HashMap<String, Object> datos;

    private final productoRepository productoRepositorio;

    @Autowired
    public productoService(productoRepository productoRepositorio) {
        this.productoRepositorio = productoRepositorio;
    }

    public List<producto> getProductos() {
        return this.productoRepositorio.findAll();
    }

    public List<producto> getProductosHomeAndroid(){
        List<producto> listaProductos  = this.productoRepositorio.findAll();
        for(int i = 0; i < listaProductos.size(); i++){
            if(listaProductos.size() == 10){
                return listaProductos;
            }else{
                Random posicion = new Random();
                int index = posicion.nextInt(listaProductos.size());
                listaProductos.remove(index);
                i = -1;
            }
        }
        if(listaProductos.size() == 10){
            return listaProductos;
        }else{
            return listaProductos;
        }
    }

    public ResponseEntity<Object> getProducto(int id) {
        Optional<producto> producto = this.productoRepositorio.findById(id);
        if (producto.isPresent()) {
            return ResponseEntity.ok().body(producto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<producto> getProductosByCategoria(String categoria) {
        return this.productoRepositorio.findByCategoria(categoria);
    }

    public List<producto> getProductosFavoritosByUsuario(int id) {
        List<producto> producto = this.productoRepositorio.findAll();
        List<producto> listaProductos = new ArrayList<producto>();
        for (int i = 0; i < producto.size(); i++) {
            for (int j = 0; j < producto.get(i).getUsuariosFavoritos().size(); j++) {
                if(producto.get(i).getUsuariosFavoritos().get(j).getId() == id) {
                    listaProductos.add(producto.get(i));
                }
            }
        }
        return listaProductos;
    }


public ResponseEntity<Object> nuevoProducto(producto producto) {
    Optional<producto> res = productoRepositorio.findProductoByNombre(producto.getNombre());

    datos = new HashMap<>();

    if (res.isPresent() || producto.getId() != null) {
        datos.put("Error", true);
        datos.put("message", "Ya existe un producto con ese nombre.");
        return new ResponseEntity<>(
                datos,
                HttpStatus.CONFLICT
        );
    }

    productoRepositorio.save(producto);
    datos.put("message", "Se guardó con éxito.");
    if (producto.getId() == null) {
        datos.put("message", "Se actualizó con éxito.");
    }
    datos.put("data", producto);

    return new ResponseEntity<>(datos, HttpStatus.CREATED);
}

public ResponseEntity<Object> borrarProducto(int idProducto) {
    datos = new HashMap<>();
    boolean existe = this.productoRepositorio.existsById(idProducto);
    if (!existe) {
        datos.put("Error", true);
        datos.put("message", "No existe un producto con ese id.");
        return new ResponseEntity<>(
                datos,
                HttpStatus.CONFLICT
        );
    }
    productoRepositorio.deleteById(idProducto);
    datos.put("message", "Producto Eliminado.");
    return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
}
}
