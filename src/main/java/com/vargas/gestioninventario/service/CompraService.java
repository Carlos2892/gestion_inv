package com.vargas.gestioninventario.service;

import com.vargas.gestioninventario.dto.CompraDTO;
import com.vargas.gestioninventario.dto.CompraSaveDTO;
import com.vargas.gestioninventario.entity.Compra;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface CompraService {
    List<Compra> findAll();
    Compra findById(Long id);
    Compra save(Compra compra);
    void deleteById(Long id);
    List<CompraDTO> listarCompras();
    void registrarCompra(CompraSaveDTO compraSaveDTO);
    void editarCompra(CompraSaveDTO compraSaveDTO);
    CompraDTO obtenerCompraPorId(Long compraId);
    void confirmarCompra(Long compraId);
    ByteArrayInputStream generarPdfCompra(Long compraId);
}
