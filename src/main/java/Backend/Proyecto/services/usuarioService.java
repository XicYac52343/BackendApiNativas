package Backend.Proyecto.services;


import Backend.Proyecto.models.producto;
import Backend.Proyecto.models.usuario;
import Backend.Proyecto.repositories.carritoRepository;
import Backend.Proyecto.repositories.productoRepository;
import Backend.Proyecto.repositories.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class usuarioService {
    HashMap<String, Object> datos;

    private final usuarioRepository usuarioRepository;
    private final productoRepository productoRepository;
    private final carritoService carritoService;

    @Autowired
    public usuarioService(usuarioRepository usuarioRepository, Backend.Proyecto.repositories.productoRepository productoRepository, carritoService carritoService) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.carritoService = carritoService;
    }

    public List<usuario> getUsuarios(){
        return this.usuarioRepository.findAll();
    }

    public ResponseEntity<Object> getUsuario(int id){
        Optional<usuario> res = this.usuarioRepository.findById(id);
        if(res.isPresent()){
            return ResponseEntity.ok().body(res.get());
        }else{
            datos.put("Error", true);
            datos.put("message", "Usuario no encontrado");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> crearUsuario(usuario usuario){
        Optional<usuario> res = this.usuarioRepository.findById(usuario.getId());
        datos = new HashMap<>();
        if(res.isPresent()){
            datos.put("message", "Usuario ya existe");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }else{
            this.usuarioRepository.save(usuario);
            this.carritoService.nuevoCarritoUsuario(usuario);
            datos.put("message", "Usuario creado");
            return new ResponseEntity<>(datos, HttpStatus.CREATED);
        }
    }

    public ResponseEntity<Object> editarUsuario(usuario usuario){
        Optional<usuario> res = this.usuarioRepository.findById(usuario.getId());
        datos = new HashMap<>();

        if(res.isPresent()){
            this.usuarioRepository.save(usuario);
            return ResponseEntity.ok().body(res.get());
        }else{
            datos.put("Error", true);
            datos.put("message", "Usuario no encontrado");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> editarUsuarioByAndroid(usuario usuario){
        Optional<usuario> res = this.usuarioRepository.findById(usuario.getId());
        datos = new HashMap<>();
        if(res.isPresent()){
            usuario nuevoUsuario = res.get();
            nuevoUsuario.setNombre(usuario.getNombre());
            nuevoUsuario.setApellido(usuario.getApellido());
            nuevoUsuario.setCorreo(usuario.getCorreo());
            nuevoUsuario.setTelefono(usuario.getTelefono());
            this.usuarioRepository.save(nuevoUsuario);
            return ResponseEntity.ok().body(nuevoUsuario);
        }else{
            datos.put("Error", true);
            datos.put("message", "Usuario no encontrado");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> editarListaFavoritosUsuario(int productoID, usuario usuario){
        Optional<usuario> res = this.usuarioRepository.findById(usuario.getId());
        datos = new HashMap<>();
        if(res.isPresent()){
            usuario usuarioActualizado = res.get();
            List<producto> listaProductos = usuarioActualizado.getProductosFavoritos();
            for(int i = 0; i < listaProductos.size(); i++){
                if(listaProductos.get(i).getId() == productoID){
                    listaProductos.remove(i);
                    usuarioActualizado.setProductosFavoritos(listaProductos);
                    this.usuarioRepository.save(usuarioActualizado);
                    datos.put("message", "Producto Eliminado");
                    return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
                }
            }
            Optional<producto> res1 = this.productoRepository.findById(productoID);
            listaProductos.add(res1.get());
            usuarioActualizado.setProductosFavoritos(listaProductos);
            this.usuarioRepository.save(usuarioActualizado);
            datos.put("message", "Producto Agregado");
            return new ResponseEntity<>(datos, HttpStatus.CREATED);
        }else{
            datos.put("Error", true);
            datos.put("message", "Usuario no encontrado");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> eliminarUsuario(int id){
        Optional<usuario> res = this.usuarioRepository.findById(id);
        datos = new HashMap<>();
        if(res.isPresent()){
            datos.put("message", "Usuario eliminado");
            this.usuarioRepository.deleteById(id);
            return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
        }else{
            datos.put("Error", true);
            datos.put("message", "Usuario no encontrado");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> iniciarSesion(usuario usuario){
        List<usuario> listaUsuario = this.usuarioRepository.findAll();
        for(int i = 0; i < listaUsuario.size(); i++){
            if(listaUsuario.get(i).getCorreo().equals(usuario.getCorreo()) && listaUsuario.get(i).getContrasenia().equals(usuario.getContrasenia())){
                return ResponseEntity.ok().body(listaUsuario.get(i).getId());
            }
        }
        return ResponseEntity.notFound().build();
    }
}
