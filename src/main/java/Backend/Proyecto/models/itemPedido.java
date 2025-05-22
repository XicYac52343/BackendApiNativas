package Backend.Proyecto.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table
public class itemPedido {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name="pedido_id", nullable = false)
    @JsonBackReference(value="hola")
    private pedido pedido;

    @ManyToOne @JoinColumn(name="producto_id", nullable = false)
    private producto producto;

    private int cantidad;

    public itemPedido() {

    }

    public itemPedido(Integer id, Backend.Proyecto.models.pedido pedido, Backend.Proyecto.models.producto producto, int cantidad) {
        this.id = id;
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Backend.Proyecto.models.pedido getPedido() {
        return pedido;
    }

    public void setPedido(Backend.Proyecto.models.pedido pedido) {
        this.pedido = pedido;
    }

    public Backend.Proyecto.models.producto getProducto() {
        return producto;
    }

    public void setProducto(Backend.Proyecto.models.producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
