package com.vargas.gestioninventario.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventarioSaveDTO {
    private String descripcion;
    private double precioVenta;
    private int minimoStock;
    private int stockActual;
    private Long idCategoria;
    private Long idMarca;
    private List<Long> idTallas;
    private String estadoProductoTalla;
}
