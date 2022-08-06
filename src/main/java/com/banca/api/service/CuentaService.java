package com.banca.api.service;

import com.banca.api.core.base.BaseService;
import com.banca.api.core.utils.ResponseWrapper;
import com.banca.api.entity.Cuenta;
import com.banca.api.repo.ClienteRepo;
import com.banca.api.repo.CuentaRepo;
import com.banca.api.repo.PersonaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CuentaService extends BaseService<CuentaService, Cuenta> {

    @Autowired
    private CuentaRepo cuentaRepo;
    @Autowired
    private ClienteRepo clienteRepo;
    @Autowired
    private PersonaRepo personaRepo;

    public CuentaService() {
        super(CuentaService.class);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ResponseWrapper<List<Cuenta>>> getAll() {
        List<Cuenta> cuentas = cuentaRepo.findAll();
        if (!cuentas.isEmpty()) {

            return super.buildResponse(cuentas);
        }
        return (ResponseEntity<ResponseWrapper<List<Cuenta>>>) super.buildResponse();
    }

    public ResponseEntity<ResponseWrapper<Cuenta>> create(Cuenta cuenta) {

        if (cuenta.getCliente() != null && cuenta.getCliente().getId() != null) {
            cuentaRepo.save(cuenta);
            return (ResponseEntity<ResponseWrapper<Cuenta>>) super.buildResponse(cuenta);
        } else {
            return (ResponseEntity<ResponseWrapper<Cuenta>>) super.buildResponse(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ResponseWrapper<List<Cuenta>>> create(List<Cuenta> cuentas) {
        String error = "";
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getCliente() == null || cuenta.getCliente().getPersona() == null || cuenta.getCliente().getPersona().getIdentificacion().trim().isEmpty()) {
                super.LOG.error("La cuenta debe tener un cliente :" + cuenta.getNumeroCuenta());
                continue;
            }
            var cuentaDb = cuentaRepo.findOne((root, query, cb) -> cb.equal(root.get("numeroCuenta"), cuenta.getNumeroCuenta()));
            var persona = personaRepo.findAll((root, query, cb) -> cb.equal(root.get("identificacion"), cuenta.getCliente().getPersona().getIdentificacion()));
            super.LOG.error("El número de identificación es:" + cuenta.getCliente().getPersona().getIdentificacion());

            if (cuentaDb.isPresent()) {
                error += (error.isEmpty() ? "" : ",") + "\"cuenta\":\"" + cuenta.getNumeroCuenta() + "\"";
                cuenta.setNumeroCuenta(cuenta.getNumeroCuenta() + "(ERROR)");
                super.LOG.error("La cuenta existe no puede ser ingresada nuevamente :" + cuenta.getNumeroCuenta());
            } else {
                cuenta.setCliente(persona.get(0).getCliente());
                cuentaRepo.save(cuenta);
            }
        }
        if (!error.isEmpty()) {
            return (ResponseEntity<ResponseWrapper<List<Cuenta>>>) super.buildBadResponse(HttpStatus.CONFLICT, "{" + error + "}");
        }
        return (ResponseEntity<ResponseWrapper<List<Cuenta>>>) super.buildResponse(cuentas);
    }
}
