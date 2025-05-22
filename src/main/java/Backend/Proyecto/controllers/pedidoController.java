package Backend.Proyecto.controllers;


import Backend.Proyecto.models.pedido;
import Backend.Proyecto.services.pedidoService;
import Backend.Proyecto.services.realizarPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pedidos")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class pedidoController {
    private final pedidoService pedidoService;
    private final realizarPedidoService realizarPedidoService;

    @Autowired
    public pedidoController(pedidoService pedidoService, realizarPedidoService realizarPedidoService) {
        this.pedidoService = pedidoService;
        this.realizarPedidoService = realizarPedidoService;
    }

    @GetMapping
    public List<pedido> getPedidos(){
        return pedidoService.getPedidos();
    }

    @GetMapping(path="pedidoID")
    public ResponseEntity<Object> getPedidoID(@PathVariable("pedidoID") Integer iDPedido){
        return this.pedidoService.getPedidoById(iDPedido);
    }

    @GetMapping(path="usuarioID")
    public List<pedido> getPedidoUsuarioID(@RequestParam(name="usuarioID", required = true) Integer iDUsuario){
        return this.pedidoService.getPedidoUsuario(iDUsuario);
    }

    @PostMapping(path="admin")
    public ResponseEntity<Object> crearPedido(@RequestBody pedido pedido){
        return this.pedidoService.crearPedido(pedido);
    }

    @PostMapping
    public ResponseEntity<Object> crearPedidoUsuarioID(@RequestParam(name="usuarioID", required = false) Integer usuarioID){
        return this.realizarPedidoService.realizarPedido(usuarioID);
    }

    @PutMapping
    public ResponseEntity<Object> modificarPedido(@RequestBody pedido pedido){
        return this.pedidoService.crearPedido(pedido);
    }

    @DeleteMapping(path="pedidoID")
    public ResponseEntity<Object> eliminarPedido(@PathVariable("pedidoID") Integer iDPedido){
        return this.pedidoService.eliminarPedido(iDPedido);
    }
}
