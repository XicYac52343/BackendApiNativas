package Backend.Proyecto.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
public class pedido {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate fecha;
    private float total;
    private float precioEnvio;
    private String estado;

    @ManyToOne
    @JoinColumn(name="usuario_id")
    @JsonBackReference
    private usuario usuario;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value="hola")
    @JsonIgnore
    private List<itemPedido> listaItems;

    public pedido() {

    }

    public pedido(Integer id, LocalDate fecha, float total, float precioEnvio, String estado, Backend.Proyecto.models.usuario usuario, List<itemPedido> listaItems) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.precioEnvio = precioEnvio;
        this.estado = estado;
        this.usuario = usuario;
        this.listaItems = listaItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getPrecioEnvio() {
        return precioEnvio;
    }

    public void setPrecioEnvio(float precioEnvio) {
        this.precioEnvio = precioEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Backend.Proyecto.models.usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Backend.Proyecto.models.usuario usuario) {
        this.usuario = usuario;
    }

    public List<itemPedido> getListaItems() {
        return listaItems;
    }

    public void setListaItems(List<itemPedido> listaItems) {
        this.listaItems = listaItems;
    }
}
