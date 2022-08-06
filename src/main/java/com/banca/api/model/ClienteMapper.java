package com.banca.api.model;

import com.banca.api.entity.Persona;

import java.util.ArrayList;
import java.util.List;

public class ClienteMapper {

    ClienteMapper() {
    }
//
//    public static ClienteModel formPersona(Persona persona) {
//        ClienteModel cliente = new ClienteModel();
//        return cliente;
//    }

    public static Persona clienteToPersona(ClienteModel cliente) {
        Persona persona = new Persona();
        persona.setId(cliente.getId());
        persona.setNombre(cliente.getNombre());
        persona.setGenero(cliente.getGenero());
        persona.setEdad(cliente.getEdad());
        persona.setIdentificacion(cliente.getIdentificacion());
        persona.setDireccion(cliente.getDireccion());
        persona.setTelefono(cliente.getTelefono());
        persona.setEstado(cliente.getEstado());
        return persona;
    }

    public static ClienteModel personaToCliente(Persona persona) {
        ClienteModel cliente = new ClienteModel();
        cliente.setId(persona.getId());
        cliente.setNombre(persona.getNombre());
        cliente.setGenero(persona.getGenero());
        cliente.setEdad(persona.getEdad());
        cliente.setIdentificacion(persona.getIdentificacion());
        cliente.setDireccion(persona.getDireccion());
        cliente.setTelefono(persona.getTelefono());
        cliente.setEstado(persona.getEstado());
        if (persona.getCliente() != null) {
            cliente.setContrasenia(persona.getCliente().getClave());
        }
        return cliente;
    }

    public static List<ClienteModel> personasToClientes(List<Persona> personas) {
        List<ClienteModel> clientes = new ArrayList<>();
        for (Persona p : personas) {
            clientes.add(personaToCliente(p));
        }
        return clientes;
    }
}
