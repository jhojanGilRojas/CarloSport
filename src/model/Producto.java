package model;

import lombok.Data;

@Data
public class Producto {
    private int IdProducto;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;

}
