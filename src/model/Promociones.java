package model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Promociones {
    private int idPromocion;
    private String descripcion;
    private float descuento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
