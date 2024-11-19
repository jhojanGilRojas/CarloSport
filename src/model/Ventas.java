package model;

import lombok.Data;
import model.enums.EstadoVenta;

import java.time.LocalDate;

@Data
public class Ventas {
    private int idVenta;
    private int idAfiliado;
    private LocalDate fechaVenta;
    private float totalVenta;
    private EstadoVenta estadoVenta;

}
