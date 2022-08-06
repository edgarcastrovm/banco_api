package com.banca.api.controller;

import com.banca.api.core.utils.ResponseWrapper;
import com.banca.api.entity.DetalleMovimientoView;
import com.banca.api.model.MovimientoModel;
import com.banca.api.service.DetalleMovimientoService;
import com.banca.api.service.MovimientosService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@Slf4j(topic = "MOVIMIENTOS_CONTROLLER")
@RestController
@RequestMapping(value = "/api/movimientos", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class MovimientosController {
    @Autowired
    private MovimientosService service;
    @Autowired
    private DetalleMovimientoService detalleService;

    @GetMapping()
    public ResponseEntity<ResponseWrapper<List<DetalleMovimientoView>>> getAll() {
        return this.detalleService.getAll();
    }
    @GetMapping("/{cliente}")
    public ResponseEntity<ResponseWrapper<List<DetalleMovimientoView>>> getByClient(@PathVariable Long cliente) {
        return this.detalleService.getByClient(cliente);
    }
    @GetMapping("/{cliente}/{fecha}")
    public ResponseEntity<ResponseWrapper<List<DetalleMovimientoView>>> getByClientAndFecha(@PathVariable int cliente,@PathVariable String fecha) throws ParseException {
        return this.detalleService.getByClientAndFecha(cliente,fecha);
    }

    @PostMapping("")
    public ResponseEntity<ResponseWrapper<MovimientoModel>> create(@RequestBody @Valid MovimientoModel movimiento) {
        return this.service.create(movimiento);
    }
}
