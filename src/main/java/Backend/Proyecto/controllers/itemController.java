package Backend.Proyecto.controllers;


import Backend.Proyecto.models.item;
import Backend.Proyecto.services.itemService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/items")
@CrossOrigin(origins = "*")


public class itemController {
    private final itemService itemService;

    @Autowired //Inyecta dependencias
    public itemController(itemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<item> getItems(){
        return this.itemService.getItems();
    }

    @GetMapping(path="{itemID}")
    public ResponseEntity<Object> getItem(@PathVariable("itemID") Integer id){
        return this.itemService.getItem(id);
    }


    @GetMapping(path="usuario")
    public List<item> getItemsByUsuario(@RequestParam(name="id", required=false) Integer id){
        return this.itemService.getItemsByUsuario(id);
    }

    @PostMapping
    public ResponseEntity<Object> crearItem(@RequestParam(name="usuarioID", required = true) Integer usuarioID, @RequestBody item item){
        return this.itemService.nuevoItem(usuarioID, item);
    }


    @PutMapping
    public ResponseEntity<Object> actualizarItem(@RequestBody item item){
        return this.itemService.editItem(item);
    }

    @PutMapping(path="{itemID}/{cantidad}")
    public ResponseEntity<Object> actualizarCantidad(@PathVariable("itemID") Integer idItem, @PathVariable("cantidad") Integer cantidad){
        return this.itemService.editarCantidadItem(idItem, cantidad);
    }

    @DeleteMapping(path="{itemID}")
    public ResponseEntity<Object> eliminarItem(@PathVariable("itemID") Integer id){
        return this.itemService.eliminarItem(id);
    }
}
