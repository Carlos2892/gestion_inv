package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.dto.KardexDTO;
import com.vargas.gestioninventario.dto.KardexFilterDTO;
import com.vargas.gestioninventario.entity.Kardex;
import java.time.LocalDate;
import java.util.List;

public interface KardexService {
    List<Kardex> findAll();
    Kardex findById(Long id);
    Kardex save(Kardex kardex);
    void deleteById(Long id);
    List<KardexDTO> obtenerKardexPorProductoTalla(Long productoTallaId);
    List<KardexDTO> filtrarKardex(KardexFilterDTO filtro);
}
