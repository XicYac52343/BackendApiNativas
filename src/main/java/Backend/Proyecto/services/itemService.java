package Backend.Proyecto.services;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import Backend.Proyecto.models.carrito;
import Backend.Proyecto.models.producto;
import Backend.Proyecto.models.item;
import Backend.Proyecto.models.usuario;
import Backend.Proyecto.repositories.carritoRepository;
import Backend.Proyecto.repositories.itemRepository;

import Backend.Proyecto.repositories.productoRepository;
import Backend.Proyecto.repositories.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class itemService {
    private final Backend.Proyecto.repositories.carritoRepository carritoRepository;
    private final usuarioRepository usuarioRepository;
    private final Backend.Proyecto.repositories.productoRepository productoRepository;
    HashMap<String, Object> datos;


    private final itemRepository itemRepository;

    @Autowired
    public itemService(itemRepository itemRepository, carritoRepository carritoRepository, Backend.Proyecto.repositories.usuarioRepository usuarioRepository, productoRepository productoRepository) {
        this.itemRepository = itemRepository;
        this.carritoRepository = carritoRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
    }

    public List<item> getItems(){
        return itemRepository.findAll();
    }

    public ResponseEntity<Object> getItem(int id){
        Optional<item> item = itemRepository.findById(id);
        if(item.isPresent()){
            return ResponseEntity.ok().body(item.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public List<item> getItemsByUsuario(int id){
        Optional<usuario> usuario = usuarioRepository.findById(id);
        List<item> listaItemsUsuario = new ArrayList<item>();
        if(usuario.isPresent()){
            carrito carrito = usuario.get().getCarrito();
            return carrito.getItem();
        }else{
            return null;
        }
    }

    public ResponseEntity<Object> nuevoItem(Integer usuarioID, item item) {
        datos = new HashMap<>();
        if(item.getId() !=null){
            Optional<item> res = itemRepository.findById(item.getId());
            if (res.isPresent()) {
                datos.put("error", true);
                datos.put("message", "El item ya existe");
                return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
            }
        }

        Optional<usuario> usuarioRes = usuarioRepository.findById(usuarioID);

        if(!usuarioRes.isPresent()){
            datos.put("error", true);
            datos.put("message", "Carrito no existe");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }

        Optional<producto> resProducto = productoRepository.findById(item.getProducto().getId());

        if(!resProducto.isPresent()){
            datos.put("error", true);
            datos.put("message", "Producto no existe");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }

        carrito carrito = usuarioRes.get().getCarrito();
        float totalCarrito = 0;
        producto producto = resProducto.get();
        item.setProducto(producto);

        for(int i  = 0; i < carrito.getItem().size(); i++){
            if(carrito.getItem().get(i).getProducto().getId() == item.getProducto().getId()){
                if(carrito.getItem().get(i).getCantidad() < item.getCantidad()){
                    if(item.getProducto().getCantidadDescuento() > 0){
                        float precioDescuento = item.getProducto().getPrecio() - ((item.getProducto().getPrecio() * item.getProducto().getCantidadDescuento())/100);
                        totalCarrito = carrito.getTotalCarrito() + (item.getCantidad() - carrito.getItem().get(i).getCantidad()) * precioDescuento ;
                        carrito.setTotalCarrito(totalCarrito);
                        carrito.getItem().get(i).setCantidad(item.getCantidad());
                        this.carritoRepository.save(carrito);
                        datos.put("message", "Se Incremento el item del carrito con Descuento");
                        datos.put("data", carrito.getItem().get(i));
                        return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
                    }else{
                        totalCarrito = carrito.getTotalCarrito() + ((item.getCantidad() - carrito.getItem().get(i).getCantidad()) * item.getProducto().getPrecio()) ;
                        carrito.setTotalCarrito(totalCarrito);
                        carrito.getItem().get(i).setCantidad(item.getCantidad());
                        this.carritoRepository.save(carrito);
                        datos.put("message", "Se Incremento el item del carrito");
                        datos.put("data", carrito.getItem().get(i));
                        return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
                    }
                }else{
                    if(item.getCantidad() == 0){
                        if(item.getProducto().getCantidadDescuento() > 0){
                            float precioDescuento = item.getProducto().getPrecio() - ((item.getProducto().getPrecio() * item.getProducto().getCantidadDescuento())/100);
                            totalCarrito = carrito.getTotalCarrito() - (carrito.getItem().get(i).getCantidad() * precioDescuento) ;
                            carrito.setTotalCarrito(totalCarrito);
                            int idItemRemover = carrito.getItem().get(i).getId();
                            carrito.getItem().remove(i);
                            this.itemRepository.deleteById(idItemRemover);
                            this.carritoRepository.save(carrito);
                            datos.put("message", "Se eliminó con éxito el item del carrito con descuento");
                            datos.put("data", item);
                            return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
                        }else{
                            totalCarrito = carrito.getTotalCarrito() - (carrito.getItem().get(i).getCantidad() * item.getProducto().getPrecio()) ;
                            carrito.setTotalCarrito(totalCarrito);
                            int idItemRemover = carrito.getItem().get(i).getId();
                            carrito.getItem().remove(i);
                            this.itemRepository.deleteById(idItemRemover);
                            this.carritoRepository.save(carrito);
                            datos.put("message", "Se eliminó con éxito el item del carrito sin descuento");
                            datos.put("data", item);
                            return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
                        }
                    }else{
                        if(item.getProducto().getCantidadDescuento() > 0){
                            float precioDescuento = item.getProducto().getPrecio() - ((item.getProducto().getPrecio() * item.getProducto().getCantidadDescuento())/100);
                            totalCarrito = carrito.getTotalCarrito() - ((carrito.getItem().get(i).getCantidad() -item.getCantidad()) * precioDescuento) ;
                            carrito.setTotalCarrito(totalCarrito);
                            carrito.getItem().get(i).setCantidad(item.getCantidad());
                            this.carritoRepository.save(carrito);
                            datos.put("message", "Se decremento con éxito el item del carrito con descuento");
                            datos.put("data", carrito.getItem().get(i));
                            return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
                        }else{
                            totalCarrito = carrito.getTotalCarrito() - ((carrito.getItem().get(i).getCantidad() - item.getCantidad()) * item.getProducto().getPrecio());
                            carrito.setTotalCarrito(totalCarrito);
                            carrito.getItem().get(i).setCantidad(item.getCantidad());
                            this.carritoRepository.save(carrito);
                            datos.put("message", "Se Decremento el item del carrito");
                            datos.put("data", carrito.getItem().get(i));
                            return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
                        }
                    }
                }
            }
        }

        if(item.getCantidad() == 0){
            datos.put("message", "La cantidad es 0, no se agrega nada");
            datos.put("data", item);
            return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
        }

        if(item.getProducto().getCantidadDescuento() > 0){
            float precioDescuento = item.getProducto().getPrecio() - ((item.getProducto().getPrecio() * item.getProducto().getCantidadDescuento())/100);
            totalCarrito = carrito.getTotalCarrito() + (item.getCantidad()  * precioDescuento) ;
            carrito.setTotalCarrito(totalCarrito);
            item.setCarrito(carrito);
            this.itemRepository.save(item);
            datos.put("message", "Se Agrego el item al   carrito con Descuento");
            datos.put("data",item);
            return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
        }else{
            totalCarrito = carrito.getTotalCarrito() +(item.getCantidad() * item.getProducto().getPrecio());
            carrito.setTotalCarrito(totalCarrito);
            item.setCarrito(carrito);
            itemRepository.save(item);
            datos.put("message", "Se Agregó el item al carrito sin Descuento");
            datos.put("data", item);
            return new ResponseEntity<>(datos, HttpStatus.CREATED);
        }
    }

    public ResponseEntity<Object> editItem(item item){
        Optional<item> res = itemRepository.findById(item.getId());
        if(res.isPresent() && item.getId() != null){
            itemRepository.save(item);
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> editarCantidadItem( int idItem, int cantidad){
        Optional<item> res = itemRepository.findById(idItem);
        datos = new HashMap<>();
        if(res.isPresent()){
            item item = res.get();
            item.setCantidad(cantidad);
            itemRepository.save(item);
            datos.put("message", "Se actualizó la cantidad del producto");
            return new ResponseEntity<>(datos, HttpStatus.OK);
        }else{
            datos.put("error", true);
            datos.put("message", "El item no se encontró");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> eliminarItem(int idItem){
        datos = new HashMap<>();
        boolean existe = this.itemRepository.existsById(idItem);
        if(existe){
            itemRepository.deleteById(idItem);
            datos.put("message", "Item Eliminado");
            return new ResponseEntity<>(datos, HttpStatus.OK);
        }else{
            datos.put("Error", true);
            datos.put("message", "No existe un producto con ese id.");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
    }
}
