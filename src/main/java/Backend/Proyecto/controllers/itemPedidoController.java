package Backend.Proyecto.controllers;

import Backend.Proyecto.models.itemPedido;
import Backend.Proyecto.services.itemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/itemPedido")
@CrossOrigin(origins = "*")

public class itemPedidoController {
    private final itemPedidoService itemPedidoService;

    @Autowired
    public itemPedidoController(itemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @GetMapping
    public List<itemPedido> getItemsPedidos(){
        return this.itemPedidoService.getItems();
    }

    @GetMapping(path="{pedidoID}")
    public List<itemPedido> getItemsPedidosByID(@PathVariable("pedidoID") Integer pedidoID){
        return this.itemPedidoService.getItemsPedido(pedidoID);
    }

    @GetMapping(path="{itemID}")
    public ResponseEntity<Object> getItem(@PathVariable("itemID") Integer itemID){
        return this.itemPedidoService.getItem(itemID);
    }

    @PostMapping(path="{pedidoID}")
    public ResponseEntity<Object> crearItemPedido(@RequestBody itemPedido itemPedido, @PathVariable("pedidoID") Integer pedidoID){
        return this.itemPedidoService.crearItem(itemPedido, pedidoID);
    }

    @PutMapping
    public ResponseEntity<Object> editarItemPedido(@RequestBody itemPedido itemPedido){
        return this.itemPedidoService.editarItem(itemPedido);
    }

    @DeleteMapping(path="{itemID}")
    public ResponseEntity<Object> eliminarItemPedido(@PathVariable("itemID") Integer itemID){
        return this.itemPedidoService.eliminarItem(itemID);
    }
}
