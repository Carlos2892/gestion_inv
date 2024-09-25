package com.vargas.gestioninventario.service.impl;

import com.vargas.gestioninventario.dto.KardexDTO;
import com.vargas.gestioninventario.dto.KardexFilterDTO;
import com.vargas.gestioninventario.entity.Kardex;
import com.vargas.gestioninventario.repository.KardexRepository;
import com.vargas.gestioninventario.service.KardexService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KardexServiceImpl implements KardexService {
    @Autowired
    private KardexRepository kardexRepository;

    @Override
    public List<Kardex> findAll() {
        return kardexRepository.findAll();
    }

    @Override
    public Kardex findById(Long id) {
        return kardexRepository.findById(id).orElse(null);
    }

    @Override
    public Kardex save(Kardex kardex) {
        return kardexRepository.save(kardex);
    }

    @Override
    public void deleteById(Long id) {
        kardexRepository.deleteById(id);
    }
    
    @Override
    public List<KardexDTO> obtenerKardexPorProductoTalla(Long productoTallaId) {
        List<Kardex> kardexList = kardexRepository.findByProductoTallaId(productoTallaId);
        return kardexList.stream().map(this::convertirKardexADTO).collect(Collectors.toList());
    }

    @Override
    public List<KardexDTO> filtrarKardex(KardexFilterDTO filtro) {
        List<Kardex> kardexList = kardexRepository.filtrarKardex(filtro.getProductoTallaId(),
            filtro.getFechaInicio(),
            filtro.getFechaFin(),
            filtro.getConcepto());
        return kardexList.stream().map(this::convertirKardexADTO).collect(Collectors.toList());
    }

    private KardexDTO convertirKardexADTO(Kardex kardex) {
        return new KardexDTO(
            kardex.getFechaMovimiento(),
            kardex.getConcepto(),
            kardex.getReferencia(),
            kardex.getCantidad(),
            kardex.getPrecioUnitario(),
            kardex.getImporteTotal(),
            kardex.getStockAnterior(),
            kardex.getStockActual()
        );
    }
}
