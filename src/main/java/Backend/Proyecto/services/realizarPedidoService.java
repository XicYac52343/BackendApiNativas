package Backend.Proyecto.services;


import Backend.Proyecto.repositories.pedidoRepository;
import Backend.Proyecto.repositories.usuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import Backend.Proyecto.models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Service
public class realizarPedidoService {
    HashMap<String, Object> datos;


    private final itemPedidoService itemPedidoService;
    private final usuarioService usuarioService;
    private final usuarioRepository usuarioRepository;
    private final pedidoService pedidoService;
    private final itemService itemService;
    private final carritoService carritoService;
    private final pedidoRepository pedidoRepository;


    @Autowired
    public realizarPedidoService(itemPedidoService itemPedidoService, usuarioService usuarioService, Backend.Proyecto.repositories.usuarioRepository usuarioRepository, pedidoService pedidoService, itemService itemService, carritoService carritoService, Backend.Proyecto.repositories.pedidoRepository pedidoRepository) {
        this.itemPedidoService = itemPedidoService;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.pedidoService = pedidoService;
        this.itemService = itemService;
        this.carritoService = carritoService;
        this.pedidoRepository = pedidoRepository;
    }

    public ResponseEntity<Object> realizarPedido(Integer idUsuario) {
        datos = new HashMap<>();
        List<carrito> listaCarritos = this.carritoService.getCarritos();
        List<itemPedido> listaItems = new ArrayList<itemPedido>();
        ResponseEntity<Object> usuarioPedido = this.usuarioService.getUsuario(idUsuario);
        float totalPedido = 0;
        usuarioPedido.getBody();
        carrito carritoUsuario = null;
        boolean carritoEncontrado = false;

        for (int i = 0; i < listaCarritos.size(); i++) {
            if (listaCarritos.get(i).getUsuario().getId() == idUsuario) {
                carritoUsuario = listaCarritos.get(i);
                carritoEncontrado = true;
                break;
            }
        }
        if (carritoEncontrado) {
            pedido pedido = new pedido();
            usuario usuario = this.usuarioRepository.findById(idUsuario).get();
            for(int j = 0; j < carritoUsuario.getItem().size(); j++) {
                itemPedido itemPedido = new itemPedido();
                itemPedido.setCantidad(carritoUsuario.getItem().get(j).getCantidad());
                itemPedido.setProducto(carritoUsuario.getItem().get(j).getProducto());
                itemPedido.setPedido(pedido);
                float subTotal = itemPedido.getCantidad() * itemPedido.getProducto().getPrecio();
                totalPedido = totalPedido+subTotal;
                int stockProducto = itemPedido.getProducto().getStock() - itemPedido.getCantidad();
                itemPedido.getProducto().setStock(stockProducto);
                itemPedido.getProducto().setPrecio(totalPedido);
                listaItems.add(itemPedido);
            }
            pedido.setFecha(LocalDate.now());
            pedido.setTotal(totalPedido);
            pedido.setPrecioEnvio(0);
            pedido.setEstado("Realizado");
            pedido.setUsuario(usuario);
            pedido.setListaItems(listaItems);
            carritoUsuario.getItem().clear();
            carritoUsuario.setTotalCarrito(0);
            this.pedidoRepository.save(pedido);
            datos.put("message", "Pedido realizado");
            return new ResponseEntity<>(datos, HttpStatus.CREATED);
        } else {
            datos.put("Error", true);
            datos.put("message", "Carrito no encontrado");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }
    }
}
