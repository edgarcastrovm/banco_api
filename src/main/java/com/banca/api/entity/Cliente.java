package com.banca.api.entity;

import com.banca.api.core.utils.Constants;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(schema = Constants.DB_SCHEMA_API, name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clienteid", nullable = false)
    private Long id;

    @Column(name = "contrasenia")
    private String clave;

    @Column(name = "estado")
    private Boolean estado;

    @OneToOne
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @OneToMany(mappedBy="cliente")
    private Set<Cuenta> cuentas;

    public Cliente() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @JsonBackReference
    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @JsonManagedReference
    public Set<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(Set<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }
}
