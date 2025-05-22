package Backend.Proyecto.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class usuario {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String apellido;

    @Column(unique = true)
    private String correo;
    private String contrasenia;
    private String direccion;
    private String telefono;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "usuario-carrito")
    private carrito carrito;


    @OneToMany(mappedBy="usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<pedido> pedidos;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name="usuario_productoFavoritos", joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name="producto_id"))
    //Se usa para evitar ciclos al JSON, usa solo el id como referencia y no trae tod el objeto
    @JsonIgnore
    private List<producto> productosFavoritos;
    public usuario() {

    }

    public usuario(int id, String nombre, String apellido, String correo, String contrasenia, String direccion, String telefono, Backend.Proyecto.models.carrito carrito, List<pedido> pedidos, List<producto> productosFavoritos) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.direccion = direccion;
        this.telefono = telefono;
        this.carrito = carrito;
        this.pedidos = pedidos;
        this.productosFavoritos = productosFavoritos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Backend.Proyecto.models.carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Backend.Proyecto.models.carrito carrito) {
        this.carrito = carrito;
    }

    public List<pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public List<producto> getProductosFavoritos() {
        return productosFavoritos;
    }

    public void setProductosFavoritos(List<producto> productosFavoritos) {
        this.productosFavoritos = productosFavoritos;
    }
}
