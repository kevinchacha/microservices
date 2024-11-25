package com.sofka.com.cuenta_movimientos_service.controller;
import com.sofka.com.cuenta_movimientos_service.dto.response.ReporteMovimientoDTO;
import com.sofka.com.cuenta_movimientos_service.service.ReporteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final ReporteService reporteService;
    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }
    @GetMapping
    public ResponseEntity<List<ReporteMovimientoDTO>> obtenerReporte(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<ReporteMovimientoDTO> reporte = reporteService.generarReporte(clienteId, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
}
