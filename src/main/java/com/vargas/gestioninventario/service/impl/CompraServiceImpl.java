package com.vargas.gestioninventario.service.impl;

import com.itextpdf.text.Chunk;
import com.vargas.gestioninventario.dto.CompraDTO;
import com.vargas.gestioninventario.dto.CompraSaveDTO;
import com.vargas.gestioninventario.dto.DetalleCompraDTO;
import com.vargas.gestioninventario.entity.Compra;
import com.vargas.gestioninventario.entity.DetalleCompra;
import com.vargas.gestioninventario.entity.Inventario;
import com.vargas.gestioninventario.entity.Kardex;
import com.vargas.gestioninventario.entity.ProductoTalla;
import com.vargas.gestioninventario.entity.Proveedor;
import com.vargas.gestioninventario.repository.CompraRepository;
import com.vargas.gestioninventario.repository.DetalleCompraRepository;
import com.vargas.gestioninventario.repository.InventarioRepository;
import com.vargas.gestioninventario.repository.KardexRepository;
import com.vargas.gestioninventario.repository.ProductoTallaRepository;
import com.vargas.gestioninventario.repository.ProveedorRepository;
import com.vargas.gestioninventario.repository.TipoComprobanteRepository;
import com.vargas.gestioninventario.service.CompraService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

@Service
public class CompraServiceImpl implements CompraService {
    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private TipoComprobanteRepository tipoComprobanteRepository;

    @Autowired
    private ProductoTallaRepository productoTallaRepository;
    
    @Autowired
    private DetalleCompraRepository detalleCompraRepository;
    
    @Autowired
    private InventarioRepository inventarioRepository;
    
    @Autowired
    private KardexRepository kardexRepository;

    @Override
    public List<Compra> findAll() {
        return compraRepository.findAll();
    }

    @Override
    public Compra findById(Long id) {
        return compraRepository.findById(id).orElse(null);
    }

    @Override
    public Compra save(Compra compra) {
        return compraRepository.save(compra);
    }

    @Override
    public void deleteById(Long id) {
        compraRepository.deleteById(id);
    }

    
    
    @Override
    public List<CompraDTO> listarCompras() {
        return compraRepository.findAllComprasConDetalles().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public CompraDTO obtenerCompraPorId(Long compraId) {
        // Buscar la compra en la base de datos
        Compra compra = compraRepository.findById(compraId)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada con el ID: " + compraId));
        // Convertir la entidad Compra a DTO
        CompraDTO compraDTO = convertToDTO(compra);
        return compraDTO;
    }

    private CompraDTO convertToDTO(Compra compra) {
        CompraDTO dto = new CompraDTO();
        dto.setId(compra.getId());
        dto.setProveedorId(compra.getProveedor().getId());
        dto.setProveedor(compra.getProveedor().getRazonSocial());
        dto.setProveedorRuc(compra.getProveedor().getNumeroDocumento());
        dto.setFechaCompra(compra.getFecha());
        dto.setTipoComprobanteId(compra.getTipoComprobante().getId());
        dto.setComprobante(compra.getTipoComprobante().getDescripcion());
        dto.setSerie(compra.getSerie());
        dto.setCorrelativo(compra.getCorrelativo());
        dto.setTotalCompra(compra.getImporteTotal());
        dto.setEstado(compra.getEstado());
        
        // Convertir la lista de DetalleCompra a una lista de DetalleCompraDTO y agregarla al DTO
        List<DetalleCompraDTO> detalleCompraDTOs = compra.getDetalleCompras().stream()
            .map(detalle -> {
                DetalleCompraDTO detalleDto = new DetalleCompraDTO();
                detalleDto.setId(detalle.getId());
                detalleDto.setProductoTallaId(detalle.getProductoTalla().getId());
                detalleDto.setCantidad(detalle.getCantidad());
                detalleDto.setPrecioUnitario(detalle.getPrecioUnitario());
                detalleDto.setSubtotal(detalle.getSubtotal());
                detalleDto.setImpuesto(detalle.getImpuesto());
                detalleDto.setImporteTotal(detalle.getImporteTotal());
                detalleDto.setDescripcionProducto(detalle.getProductoTalla().getProducto().getDescripcion()+" - "+detalle.getProductoTalla().getProducto().getMarca().getNombre()+" - "+detalle.getProductoTalla().getTalla().getNombre());
                return detalleDto;
            })
            .collect(Collectors.toList());
        dto.setDetalleCompras(detalleCompraDTOs);
        return dto;
    }
    
    @Override
    @Transactional
    public void registrarCompra(CompraSaveDTO compraSaveDTO) {
        // Lógica para crear una nueva compra
        validarCompraDuplicada(compraSaveDTO);
        guardarCompra(compraSaveDTO, new Compra(), true);
    }

    @Override
    @Transactional
    public void editarCompra(CompraSaveDTO compraSaveDTO) {
        // Lógica para editar una compra existente
        Compra compraExistente = compraRepository.findById(compraSaveDTO.getId())
            .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        validarCompraDuplicada(compraSaveDTO, compraExistente.getId());
        guardarCompra(compraSaveDTO, compraExistente, false);
    }

    private void validarCompraDuplicada(CompraSaveDTO compraSaveDTO) {
        // Validar si ya existe un comprobante con la misma combinación de tipoComprobante, serie, correlativo y proveedor
        boolean existeComprobante = compraRepository.existsByTipoComprobante_IdAndSerieAndCorrelativoAndProveedor_Id(
                compraSaveDTO.getTipoComprobanteId(),
                compraSaveDTO.getSerie(),
                compraSaveDTO.getCorrelativo(),
                compraSaveDTO.getProveedorId()
        );

        if (existeComprobante) {
            Proveedor proveedor = proveedorRepository.findById(compraSaveDTO.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

            String mensaje = "El comprobante de compra: " + compraSaveDTO.getSerie() + "-" + compraSaveDTO.getCorrelativo() + 
                             " del proveedor " + proveedor.getRazonSocial() + " ya fue registrado anteriormente.";
            throw new RuntimeException(mensaje);
        }
    }

    private void validarCompraDuplicada(CompraSaveDTO compraSaveDTO, Long compraId) {
        // Validar excluyendo la misma compra por su id
        boolean existeComprobante = compraRepository.existsByTipoComprobante_IdAndSerieAndCorrelativoAndProveedor_IdAndIdNot(
                compraSaveDTO.getTipoComprobanteId(),
                compraSaveDTO.getSerie(),
                compraSaveDTO.getCorrelativo(),
                compraSaveDTO.getProveedorId(),
                compraId
        );

        if (existeComprobante) {
            Proveedor proveedor = proveedorRepository.findById(compraSaveDTO.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

            String mensaje = "El comprobante de compra: " + compraSaveDTO.getSerie() + "-" + compraSaveDTO.getCorrelativo() + 
                             " del proveedor " + proveedor.getRazonSocial() + " ya fue registrado anteriormente.";
            throw new RuntimeException(mensaje);
        }
    }

    private void guardarCompra(CompraSaveDTO compraSaveDTO, Compra compra, boolean esNuevo) {
        compra.setProveedor(proveedorRepository.findById(compraSaveDTO.getProveedorId())
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado")));
        compra.setFecha(compraSaveDTO.getFecha());
        compra.setTipoComprobante(tipoComprobanteRepository.findById(compraSaveDTO.getTipoComprobanteId())
            .orElseThrow(() -> new RuntimeException("Tipo de comprobante no encontrado")));
        compra.setSerie(compraSaveDTO.getSerie());
        compra.setCorrelativo(compraSaveDTO.getCorrelativo());
        compra.setSubtotal(compraSaveDTO.getSubtotal());
        compra.setImpuesto(compraSaveDTO.getImpuesto());
        compra.setImporteTotal(compraSaveDTO.getImporteTotal());
        compra.setEstado("REGISTRADO");
        compraRepository.save(compra);

        if (!esNuevo) {
            // Si es una edición, eliminar los detalles anteriores
            detalleCompraRepository.deleteByCompraId(compra.getId());
        }

        // Guardar los nuevos detalles de la compra
        for (DetalleCompraDTO detalleCompraDTO : compraSaveDTO.getDetalleCompras()) {
            DetalleCompra detalleCompra = new DetalleCompra();
            detalleCompra.setProductoTalla(productoTallaRepository.findById(detalleCompraDTO.getProductoTallaId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado")));
            detalleCompra.setCantidad(detalleCompraDTO.getCantidad());
            detalleCompra.setPrecioUnitario(detalleCompraDTO.getPrecioUnitario());
            detalleCompra.setImpuesto(detalleCompraDTO.getImpuesto());
            detalleCompra.setSubtotal(detalleCompraDTO.getSubtotal());
            detalleCompra.setImporteTotal(detalleCompraDTO.getImporteTotal());
            detalleCompra.setCompra(compra);
            detalleCompraRepository.save(detalleCompra);
        }
    }
    
    @Override
    @Transactional
    public void confirmarCompra(Long compraId) {
    Compra compra = compraRepository.findById(compraId)
        .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
        // Cambiar estado a "CONFIRMADO"
        compra.setEstado("CONFIRMADO");
        compraRepository.save(compra);

        // Actualizar stock y registrar en Kardex
        for (DetalleCompra detalleCompra : compra.getDetalleCompras()) {
            ProductoTalla productoTalla = detalleCompra.getProductoTalla();

            // Obtener el inventario correspondiente
            Inventario inventario = inventarioRepository.findByProductoTalla(productoTalla);

            // Actualizar stock actual en el inventario
            int nuevoStock = inventario.getStockActual() + detalleCompra.getCantidad();
            inventario.setStockActual(nuevoStock);
            inventario.setFechaActualizacion(LocalDateTime.now());
            inventarioRepository.save(inventario);

            // Registrar el movimiento en Kardex
            Kardex kardex = new Kardex();
            kardex.setProductoTalla(productoTalla);
            kardex.setFechaMovimiento(LocalDate.now());
            kardex.setConcepto("COMPRA");
            kardex.setCantidad(detalleCompra.getCantidad());
            kardex.setPrecioUnitario(detalleCompra.getPrecioUnitario());
            kardex.setImporteTotal(detalleCompra.getImporteTotal());
            kardex.setStockAnterior(inventario.getStockActual() - detalleCompra.getCantidad());
            kardex.setStockActual(nuevoStock);
            kardex.setReferencia("Compra ID: " + compra.getId());
            kardexRepository.save(kardex);
            
            // Recalcular el precio de compra promedio
            recalcularPrecioCompra(productoTalla.getId());
        }
    }

    private void recalcularPrecioCompra(Long productoTallaId) {
        Double nuevoPrecioPromedio = kardexRepository.findPrecioPromedioPonderado(productoTallaId);
        ProductoTalla productoTalla = productoTallaRepository.findById(productoTallaId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productoTalla.setPrecioCompra(nuevoPrecioPromedio);
        productoTallaRepository.save(productoTalla);
    }
    
    @Override
    public ByteArrayInputStream generarPdfCompra(Long compraId) {
        Compra compra = compraRepository.findById(compraId)
            .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Fuente
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font regularFont = new Font(Font.FontFamily.HELVETICA, 12);

            // Encabezado
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new int[]{3, 1});

            PdfPCell titleCell = new PdfPCell(new Phrase("REGISTRO DE COMPRA", titleFont));
            titleCell.setBorder(PdfPCell.NO_BORDER);
            titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerTable.addCell(titleCell);

            PdfPCell logoCell = new PdfPCell();
            logoCell.setBorder(PdfPCell.NO_BORDER);
            logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            // logoCell.setImage(...); // Aquí podrías agregar una imagen/logo si lo deseas
            headerTable.addCell(logoCell);

            document.add(headerTable);
            document.add(Chunk.NEWLINE);

            // Información de la compra
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingBefore(10);
            infoTable.setSpacingAfter(10);
            infoTable.setWidths(new int[]{1, 3});

            infoTable.addCell(getCell("Nro. Compra:", PdfPCell.ALIGN_LEFT, boldFont));
            infoTable.addCell(getCell(compra.getId().toString(), PdfPCell.ALIGN_LEFT, regularFont));

            infoTable.addCell(getCell("Fecha:", PdfPCell.ALIGN_LEFT, boldFont));
            infoTable.addCell(getCell(compra.getFecha().toString(), PdfPCell.ALIGN_LEFT, regularFont));

            document.add(infoTable);

            // Datos del proveedor
            PdfPTable proveedorTable = new PdfPTable(1);
            proveedorTable.setWidthPercentage(100);
            proveedorTable.setSpacingBefore(10);
            proveedorTable.setSpacingAfter(10);

            proveedorTable.addCell(getCell("DATOS DEL PROVEEDOR", PdfPCell.ALIGN_LEFT, boldFont));

            proveedorTable.addCell(getCell("Ruc: " + compra.getProveedor().getNumeroDocumento(), PdfPCell.ALIGN_LEFT, regularFont));
            proveedorTable.addCell(getCell("Razón Social: " + compra.getProveedor().getRazonSocial(), PdfPCell.ALIGN_LEFT, regularFont));
            proveedorTable.addCell(getCell("Dirección: " + compra.getProveedor().getDireccion(), PdfPCell.ALIGN_LEFT, regularFont));
            proveedorTable.addCell(getCell("Teléfono: " + compra.getProveedor().getTelefono(), PdfPCell.ALIGN_LEFT, regularFont));

            document.add(proveedorTable);

            // Tabla de detalles de la compra
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(20);
            table.setWidths(new int[]{1, 3, 1, 1, 1});

            // Encabezados de la tabla
            table.addCell(getCell("Código", PdfPCell.ALIGN_CENTER, boldFont));
            table.addCell(getCell("Descripción", PdfPCell.ALIGN_CENTER, boldFont));
            table.addCell(getCell("Cant.", PdfPCell.ALIGN_CENTER, boldFont));
            table.addCell(getCell("P. Unit.", PdfPCell.ALIGN_CENTER, boldFont));
            table.addCell(getCell("Total", PdfPCell.ALIGN_CENTER, boldFont));

            // Detalles de la compra
            for (DetalleCompra detalle : compra.getDetalleCompras()) {
                table.addCell(getCell(String.valueOf(detalle.getProductoTalla().getId()), PdfPCell.ALIGN_CENTER, regularFont));
                table.addCell(getCell(detalle.getProductoTalla().getProducto().getDescripcion(), PdfPCell.ALIGN_LEFT, regularFont));
                table.addCell(getCell(String.valueOf(detalle.getCantidad()), PdfPCell.ALIGN_CENTER, regularFont));
                table.addCell(getCell(String.format("S/ %.2f", detalle.getPrecioUnitario()), PdfPCell.ALIGN_CENTER, regularFont));
                table.addCell(getCell(String.format("S/ %.2f", detalle.getImporteTotal()), PdfPCell.ALIGN_CENTER, regularFont));
            }

            document.add(table);

            // Resumen de la compra
            PdfPTable resumenTable = new PdfPTable(2);
            resumenTable.setWidthPercentage(30);
            resumenTable.setSpacingBefore(20);
            resumenTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

            resumenTable.addCell(getCell("Subtotal:", PdfPCell.ALIGN_LEFT, boldFont));
            resumenTable.addCell(getCell("S/ " + String.format("%.2f", compra.getSubtotal()), PdfPCell.ALIGN_RIGHT, regularFont));

            resumenTable.addCell(getCell("IGV:", PdfPCell.ALIGN_LEFT, boldFont));
            resumenTable.addCell(getCell("S/ " + String.format("%.2f", compra.getImpuesto()), PdfPCell.ALIGN_RIGHT, regularFont));

            resumenTable.addCell(getCell("Total:", PdfPCell.ALIGN_LEFT, boldFont));
            resumenTable.addCell(getCell("S/ " + String.format("%.2f", compra.getImporteTotal()), PdfPCell.ALIGN_RIGHT, regularFont));

            document.add(resumenTable);

            document.close();

        } catch (DocumentException ex) {
            throw new RuntimeException("Error al generar el PDF de la compra", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    // Helper method for creating cells
    private PdfPCell getCell(String text, int alignment, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

}
