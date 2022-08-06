package com.banca.api.controller;

import com.banca.api.core.utils.ResponseWrapper;
import com.banca.api.entity.Cuenta;
import com.banca.api.service.CuentaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j(topic = "CUENTA_CONTROLLER")
@RestController
@RequestMapping(value = "/api/cuentas", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class CuentaController {
    @Autowired
    private CuentaService service;

    @GetMapping()
    public ResponseEntity<ResponseWrapper<List<Cuenta>>> getAll() {
        return this.service.getAll();
    }
    /*
    @PostMapping("")
    public ResponseEntity<ResponseWrapper<Cuenta>> create(@RequestBody @Valid Cuenta cuenta) {
        return this.service.create(cuenta);
    }*/
    @PostMapping("")
    public ResponseEntity<ResponseWrapper<List<Cuenta>>> create(@RequestBody @Valid List<Cuenta> cuentas) {
        return this.service.create(cuentas);
    }
}
