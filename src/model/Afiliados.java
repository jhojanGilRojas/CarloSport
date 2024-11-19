package model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Afiliados {

    private int idAfiliado;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private int id_Superior;
    private LocalDate fechaAfiliacion;
    private int nivel;

}
