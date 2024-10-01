package com.vargas.gestioninventario.service.impl;

import com.itextpdf.text.Chunk;
import com.vargas.gestioninventario.dto.DetalleVentaDTO;
import com.vargas.gestioninventario.dto.VentaDTO;
import com.vargas.gestioninventario.dto.VentaSaveDTO;
import com.vargas.gestioninventario.entity.DetalleVenta;
import com.vargas.gestioninventario.entity.Inventario;
import com.vargas.gestioninventario.entity.Kardex;
import com.vargas.gestioninventario.entity.ProductoTalla;
import com.vargas.gestioninventario.entity.Venta;
import com.vargas.gestioninventario.repository.ClienteRepository;
import com.vargas.gestioninventario.repository.DetalleVentaRepository;
import com.vargas.gestioninventario.repository.FormaPagoRepository;
import com.vargas.gestioninventario.repository.InventarioRepository;
import com.vargas.gestioninventario.repository.KardexRepository;
import com.vargas.gestioninventario.repository.ProductoTallaRepository;
import com.vargas.gestioninventario.repository.VentaRepository;
import com.vargas.gestioninventario.service.VentaService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
public class VentaServiceImpl implements VentaService {
    @Autowired
    private VentaRepository ventaRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ProductoTallaRepository productoTallaRepository;
    
    @Autowired
    private FormaPagoRepository formaPagoRepository;
    
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    
    @Autowired
    private InventarioRepository inventarioRepository;
    
    @Autowired
    private KardexRepository kardexRepository;

    @Override
    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta findById(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    public Venta save(Venta venta) {
        return ventaRepository.save(venta);
    }

    @Override
    public void deleteById(Long id) {
        ventaRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VentaDTO> listarVentas() {
        List<Venta> ventas = ventaRepository.findAll();
        return ventas.stream().map(this::convertirADTO).collect(Collectors.toList());
    }
    
    private VentaDTO convertirADTO(Venta venta) {
        VentaDTO dto = new VentaDTO();
        dto.setId(venta.getId());
        dto.setCorrelativo(venta.getCorrelativo());
        dto.setFecha(venta.getFecha());
        dto.setSubtotal(venta.getSubtotal());
        dto.setDescuento(venta.getDescuento());
        dto.setImporteTotal(venta.getImporteTotal());
        dto.setEstado(venta.getEstado());

        dto.setClienteId(venta.getCliente().getId());
        dto.setClienteNombre(venta.getCliente().getNombre());
        dto.setClienteApellido(venta.getCliente().getApellido());
        dto.setClienteNumeroDocumento(venta.getCliente().getNumeroDocumento());

        dto.setFormaPagoId(venta.getFormaPago().getId());
        dto.setFormaPagoDescripcion(venta.getFormaPago().getDescripcion());
        
        return dto;
    }

    
    @Override
    public Integer obtenerUltimoCorrelativo() {
        Integer ultimoCorrelativo = ventaRepository.obtenerUltimoCorrelativo();
        return (ultimoCorrelativo != null) ? ultimoCorrelativo : 0;
    }
    
    @Override
    @Transactional
    public Venta registrarVenta(VentaSaveDTO ventaSaveDTO) {
        // Crear una nueva instancia de Venta
        Venta venta = new Venta();

        // Asignar los valores del DTO a la entidad Venta
        venta.setCliente(clienteRepository.findById(ventaSaveDTO.getClienteId())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado")));
        venta.setFormaPago(formaPagoRepository.findById(ventaSaveDTO.getFormaPagoId())
            .orElseThrow(() -> new RuntimeException("Forma de pago no encontrado")));
        venta.setFecha(ventaSaveDTO.getFecha());
        venta.setCorrelativo(ventaSaveDTO.getCorrelativo());
        venta.setSubtotal(ventaSaveDTO.getSubtotal());
        venta.setDescuento(ventaSaveDTO.getDescuento());
        venta.setImporteTotal(ventaSaveDTO.getImporteTotal());
        venta.setEstado("REGISTRADO");

        // Guardar la venta en la base de datos
        ventaRepository.save(venta);

        // Guardar los detalles de la venta
        for (DetalleVentaDTO detalleVentaDTO : ventaSaveDTO.getDetalleVentas()) {
            DetalleVenta detalleVenta = new DetalleVenta();
            ProductoTalla productoTalla = productoTallaRepository.findById(detalleVentaDTO.getProductoTallaId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            detalleVenta.setProductoTalla(productoTalla);
            detalleVenta.setCantidad(detalleVentaDTO.getCantidad());
            detalleVenta.setPrecioCompra(detalleVentaDTO.getPrecioCompra());
            detalleVenta.setPrecioVenta(detalleVentaDTO.getPrecioUnitario());
            detalleVenta.setDescuentoAplicado(detalleVentaDTO.getDescuento());
            detalleVenta.setSubtotal(detalleVentaDTO.getSubtotal());
            detalleVenta.setImporteTotal(detalleVentaDTO.getImporteTotal());
            detalleVenta.setVenta(venta);
            // Guardar cada detalle de la venta en la base de datos
            detalleVentaRepository.save(detalleVenta);
            
            // Obtener el inventario correspondiente
            Inventario inventario = inventarioRepository.findByProductoTalla(productoTalla);
            
            // Actualizar stock actual en el inventario
            int nuevoStock = inventario.getStockActual() - detalleVenta.getCantidad();
            inventario.setStockActual(nuevoStock);
            inventario.setFechaActualizacion(LocalDateTime.now());
            inventarioRepository.save(inventario);
            
            // Registrar el movimiento en Kardex
            Kardex kardex = new Kardex();
            kardex.setProductoTalla(productoTalla);
            kardex.setFechaMovimiento(venta.getFecha());
            kardex.setConcepto("VENTA");
            kardex.setCantidad(detalleVenta.getCantidad());
            kardex.setPrecioUnitario(detalleVenta.getPrecioVenta());
            kardex.setImporteTotal(detalleVenta.getImporteTotal());
            kardex.setStockAnterior(inventario.getStockActual() + detalleVenta.getCantidad());
            kardex.setStockActual(nuevoStock);
            kardex.setReferencia("Venta ID: " + venta.getId());
            kardexRepository.save(kardex);
        }
        return venta;
    }
    
    @Override
    public ByteArrayInputStream generarPdfVenta(Long ventaId) {
        Venta venta = ventaRepository.findById(ventaId)
            .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        // Ajustamos el tamaño de la página a 80mm de ancho (3.15 pulgadas) por una altura indefinida
        Document document = new Document(new Rectangle(310, 680)); // 310 puntos = 80mm
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Fuentes
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
            Font regularFont = new Font(Font.FontFamily.HELVETICA, 10);
            Font companyFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);

            // Encabezado de la boleta
            PdfPTable headerTable = new PdfPTable(1);
            headerTable.setWidthPercentage(100);

            // Título de la boleta
            PdfPCell titleCell = new PdfPCell(new Phrase("COMPROBANTE DE VENTA", titleFont));
            titleCell.setBorder(PdfPCell.NO_BORDER);
            titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerTable.addCell(titleCell);
            
            PdfPCell companyCell = new PdfPCell(new Phrase("Modas S'Leyark", companyFont));
            companyCell.setBorder(PdfPCell.NO_BORDER);
            companyCell.setHorizontalAlignment(Element.ALIGN_CENTER);  // Centramos el texto
            headerTable.addCell(companyCell);
            

            document.add(headerTable);
            document.add(Chunk.NEWLINE);

            // Información de la empresa
            PdfPTable empresaTable = new PdfPTable(1);
            empresaTable.setWidthPercentage(100);

            // Dirección
            empresaTable.addCell(getCell("Dirección: Mercado modelo, pabellón \"Paraíso soñado stad #6\"", PdfPCell.ALIGN_LEFT, regularFont));
            
            // Celular
            empresaTable.addCell(getCell("Contacto: 923273532", PdfPCell.ALIGN_LEFT, regularFont));

            document.add(empresaTable);
            document.add(Chunk.NEWLINE);

            // Información de la venta
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingBefore(10);
            infoTable.setSpacingAfter(10);
            infoTable.setWidths(new int[]{1, 2});

            infoTable.addCell(getCell("Nro. Venta:", PdfPCell.ALIGN_LEFT, boldFont));
            infoTable.addCell(getCell(venta.getCorrelativo(), PdfPCell.ALIGN_LEFT, regularFont));

            infoTable.addCell(getCell("Fecha:", PdfPCell.ALIGN_LEFT, boldFont));
            infoTable.addCell(getCell(venta.getFecha().toString(), PdfPCell.ALIGN_LEFT, regularFont));

            infoTable.addCell(getCell("Cliente:", PdfPCell.ALIGN_LEFT, boldFont));
            infoTable.addCell(getCell(venta.getCliente().getNombre() + " " + venta.getCliente().getApellido(), PdfPCell.ALIGN_LEFT, regularFont));

            document.add(infoTable);

            // Detalles de la venta
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setWidths(new int[]{6, 2, 3, 3});

            table.addCell(getCell("Descripción", PdfPCell.ALIGN_CENTER, boldFont));
            table.addCell(getCell("Cant.", PdfPCell.ALIGN_CENTER, boldFont));
            table.addCell(getCell("P. Unit.", PdfPCell.ALIGN_CENTER, boldFont));
            table.addCell(getCell("Total", PdfPCell.ALIGN_CENTER, boldFont));

            for (DetalleVenta detalle : venta.getDetalleVentas()) {
                table.addCell(getCell(detalle.getProductoTalla().getProducto().getDescripcion() + " " + detalle.getProductoTalla().getProducto().getMarca().getNombre() + " T: " + detalle.getProductoTalla().getTalla().getNombre(), PdfPCell.ALIGN_LEFT, regularFont));
                table.addCell(getCell(String.valueOf(detalle.getCantidad()), PdfPCell.ALIGN_CENTER, regularFont));
                table.addCell(getCell(String.format("S/ %.2f", detalle.getPrecioVenta()), PdfPCell.ALIGN_CENTER, regularFont));
                table.addCell(getCell(String.format("S/ %.2f", detalle.getCantidad() * detalle.getPrecioVenta()), PdfPCell.ALIGN_CENTER, regularFont));
            }

            document.add(table);

            // Resumen de la venta
            PdfPTable resumenTable = new PdfPTable(2);
            resumenTable.setWidthPercentage(100);
            resumenTable.setSpacingBefore(10);
            resumenTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

            resumenTable.addCell(getCell("Subtotal:", PdfPCell.ALIGN_LEFT, boldFont));
            resumenTable.addCell(getCell("S/ " + String.format("%.2f", venta.getSubtotal()), PdfPCell.ALIGN_RIGHT, regularFont));

            resumenTable.addCell(getCell("Descuento:", PdfPCell.ALIGN_LEFT, boldFont));
            resumenTable.addCell(getCell("S/ " + String.format("%.2f", venta.getDescuento()), PdfPCell.ALIGN_RIGHT, regularFont));

            resumenTable.addCell(getCell("Total:", PdfPCell.ALIGN_LEFT, boldFont));
            resumenTable.addCell(getCell("S/ " + String.format("%.2f", venta.getImporteTotal()), PdfPCell.ALIGN_RIGHT, regularFont));

            document.add(resumenTable);

            document.close();

        } catch (DocumentException ex) {
            throw new RuntimeException("Error al generar el PDF de la venta", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private PdfPCell getCell(String text, int alignment, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(2); // Espacio dentro de la celda
        cell.setHorizontalAlignment(alignment); // Alineación del contenido dentro de la celda
        cell.setBorder(PdfPCell.NO_BORDER); // Sin bordes en la celda, puedes ajustar esto si necesitas bordes
        return cell;
    }

    
    @Override
    @Transactional(readOnly = true)
    public long contarVentasDelDia() {
        LocalDate fechaActual = LocalDate.now();
        return ventaRepository.contarVentasDelDia(fechaActual);
    }
    
    @Override
    public Double obtenerTotalVentasDelMes() {
        LocalDate fechaActual = LocalDate.now();  // Obtén la fecha actual desde Java
        Double totalVentasDelDia = ventaRepository.obtenerTotalVentasDelMes(fechaActual);
        return totalVentasDelDia != null ? totalVentasDelDia : 0.0;  // Manejo de nulls
    }
    
    @Override
    public List<Object[]> obtenerVentasMensualesDelAnio() {
        LocalDate fechaActual = LocalDate.now();
        return ventaRepository.obtenerVentasMensualesDelAnio(fechaActual);
    }
    
    @Override
    public List<Map<String, Object>> obtenerVentasPorCategoriaDelMes() {
        // Obtenemos las ventas por categoría del mes actual desde el repositorio
        List<Object[]> ventasPorCategoria = ventaRepository.obtenerVentasPorCategoriaDelMes();
        List<Map<String, Object>> resultado = new ArrayList<>();

        // Procesamos los resultados y los pasamos a un formato más amigable
        for (Object[] fila : ventasPorCategoria) {
            Map<String, Object> categoriaData = new HashMap<>();
            categoriaData.put("categoria", fila[0]);        // Nombre de la categoría
            categoriaData.put("totalVentas", fila[1]);      // Total de ventas
            resultado.add(categoriaData);
        }
        
        return resultado;  // Devolvemos el resultado procesado
    }
}
