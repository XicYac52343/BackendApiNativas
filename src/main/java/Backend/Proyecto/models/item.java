package Backend.Proyecto.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import Backend.Proyecto.models.carrito;

@Entity
@Table
public class item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne @JoinColumn(name="carrito_id", nullable = false)
    private carrito carrito;

    @ManyToOne @JoinColumn(name="producto_id", nullable = false)
    private producto producto;

    private int cantidad;

    public item(){

    }

    public item(Integer id, carrito carrito, producto producto, int cantidad) {
        this.id = id;
        this.carrito = carrito;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(carrito carrito) {
        this.carrito = carrito;
    }

    public producto getProducto() {
        return producto;
    }

    public void setProducto(producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
