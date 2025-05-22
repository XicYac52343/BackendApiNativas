package Backend.Proyecto.controllers;

import Backend.Proyecto.models.usuario;
import Backend.Proyecto.services.usuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/usuario")
@CrossOrigin(origins = "*")

public class usuarioController {
    private final usuarioService usuarioService;
    @Autowired
    public usuarioController(usuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<usuario> getUsuarios() {
        return this.usuarioService.getUsuarios();
    }

    @GetMapping(path="user")
    public ResponseEntity<Object> getUsuarios(@RequestParam(value = "id", required = false) Integer id){
        return this.usuarioService.getUsuario(id);
    }

    @PostMapping
    public ResponseEntity<Object> crearUsuario(@RequestBody usuario usuario) {
        return this.usuarioService.crearUsuario(usuario);
    }

    @PutMapping
    public ResponseEntity<Object> editarUsuario(@RequestBody usuario usuario) {
        return this.usuarioService.editarUsuario(usuario);
    }

    @PutMapping("productoID")
    public ResponseEntity<Object> editarListaFavoritosUsuario(@RequestParam(value = "productoID", required = true) Integer id, @RequestBody usuario usuario) {
        return this.usuarioService.editarListaFavoritosUsuario(id, usuario);
    }

    @PutMapping(path="userAndroid")
    public ResponseEntity<Object> editarUsuarioByAndroid( @RequestBody usuario usuario) {
        return this.usuarioService.editarUsuarioByAndroid(usuario);
    }

    @DeleteMapping(path="{usuarioID}")
    public ResponseEntity<Object> eliminarUsuario(@PathVariable("usuarioID") Integer id) {
        return this.usuarioService.eliminarUsuario(id);
    }
}
