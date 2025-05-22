package Backend.Proyecto.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy="carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    //Parte dueña de la relacion
    @JsonIgnore
    private List<item> item;

    @OneToOne
    @JsonBackReference(value = "usuario-carrito")
    @JoinColumn(name="usuario_id")
    private usuario usuario;

    private float totalCarrito = 0;


    public carrito(){

    }

    public carrito(int id, List<Backend.Proyecto.models.item> item, Backend.Proyecto.models.usuario usuario, float totalCarrito) {
        this.id = id;
        this.item = item;
        this.usuario = usuario;
        this.totalCarrito = totalCarrito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Backend.Proyecto.models.item> getItem() {
        return item;
    }

    public void setItem(List<Backend.Proyecto.models.item> item) {
        this.item = item;
    }

    public Backend.Proyecto.models.usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Backend.Proyecto.models.usuario usuario) {
        this.usuario = usuario;
    }

    public float getTotalCarrito() {
        return totalCarrito;
    }

    public void setTotalCarrito(float totalCarrito) {
        this.totalCarrito = totalCarrito;
    }
}
