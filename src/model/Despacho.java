package model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Despacho {

    private int idDespacho;
    private int idVenta;
    private LocalDate fechaDespacho;

}
