package Backend.Proyecto.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@Table
public class producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String categoria;
    private String descripcion;
    private int stock;
    private float precio;
    private int cantidadDescuento;
    private String nombreImagen;

    @ManyToMany(mappedBy="productosFavoritos")
    //Se usa para evitar ciclos al JSON, usa solo el id como referencia y no trae tod el objeto
    private List<usuario> usuariosFavoritos;

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private List<item> items;
    public producto(){

    }

    public producto(Integer id, String nombre, String categoria, String descripcion, int stock, float precio, int cantidadDescuento, List<usuario> usuariosFavoritos, List<item> items, String nombreImagen) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.stock = stock;
        this.precio = precio;
        this.cantidadDescuento = cantidadDescuento;
        this.usuariosFavoritos = usuariosFavoritos;
        this.items = items;
        this.nombreImagen = nombreImagen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantidadDescuento() {
        return cantidadDescuento;
    }

    public void setCantidadDescuento(int cantidadDescuento) {
        this.cantidadDescuento = cantidadDescuento;
    }

    public List<usuario> getUsuariosFavoritos() {
        return usuariosFavoritos;
    }

    public void setUsuariosFavoritos(List<usuario> usuariosFavoritos) {
        this.usuariosFavoritos = usuariosFavoritos;
    }

    public List<item> getItems() {
        return items;
    }

    public void setItems(List<item> items) {
        this.items = items;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }
    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }
}
