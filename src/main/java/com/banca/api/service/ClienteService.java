package com.banca.api.service;

import com.banca.api.core.base.BaseService;
import com.banca.api.core.utils.ResponseWrapper;
import com.banca.api.entity.Cliente;
import com.banca.api.entity.Persona;
import com.banca.api.model.ClienteMapper;
import com.banca.api.model.ClienteModel;
import com.banca.api.repo.ClienteRepo;
import com.banca.api.repo.PersonaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService extends BaseService<ClienteService, ClienteModel> {

    @Autowired
    private PersonaRepo personaRepo;
    @Autowired
    private ClienteRepo clienteRepo;

    public ClienteService() {
        super(ClienteService.class);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ResponseWrapper<List<ClienteModel>>> getAll() {
        List<Persona> personas = personaRepo.findAll();
        if (!personas.isEmpty()) {
            List<ClienteModel> clientes = ClienteMapper.personasToClientes(personas);
            return (ResponseEntity<ResponseWrapper<List<ClienteModel>>>) super.buildResponse(clientes);
        }
        return (ResponseEntity<ResponseWrapper<List<ClienteModel>>>) super.buildResponse();
    }

    public ResponseEntity<ResponseWrapper<ClienteModel>> create(ClienteModel clienteModel) {
        var personaDb = personaRepo.findAll((root, query, cb) -> cb.equal( root.get("identificacion"), clienteModel.getIdentificacion()));
        if(!personaDb.isEmpty()){
            super.LOG.info("Se trato de ingreesar un cliente que ya existe identificación:"+ personaDb.get(0).getIdentificacion());
            return (ResponseEntity<ResponseWrapper<ClienteModel>>) super.buildBadResponse(HttpStatus.CONFLICT);
        }
        try {
            Cliente cliente = new Cliente();
            Persona persona = ClienteMapper.clienteToPersona(clienteModel);
            personaRepo.save(persona);
            cliente.setClave(clienteModel.getContrasenia());
            cliente.setEstado(clienteModel.getEstado());
            cliente.setPersona(persona);
            clienteRepo.save(cliente);
            personaRepo.flush();
            clienteRepo.flush();
            persona.setCliente(cliente);
            super.LOG.info("CLAVE", persona.getCliente().getClave());
            return (ResponseEntity<ResponseWrapper<ClienteModel>>) super.buildResponse(ClienteMapper.personaToCliente(persona));
        } catch (Exception ex) {
            return (ResponseEntity<ResponseWrapper<ClienteModel>>) super.buildBadResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
