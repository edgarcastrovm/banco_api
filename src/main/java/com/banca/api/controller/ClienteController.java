package com.banca.api.controller;

import com.banca.api.core.utils.ResponseWrapper;
import com.banca.api.model.ClienteModel;
import com.banca.api.service.ClienteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j(topic = "CLIENTE_CONTROLLER")
@RestController
@RequestMapping(value = "/api/clientes", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class ClienteController {
    @Autowired
    private ClienteService service;

    @GetMapping()
    public ResponseEntity<ResponseWrapper<List<ClienteModel>>> getAll() {
        return this.service.getAll();
    }

    @PostMapping("")
    public ResponseEntity<ResponseWrapper<ClienteModel>> create(@RequestBody @Valid ClienteModel clientes) {
        return this.service.create(clientes);
    }
}
