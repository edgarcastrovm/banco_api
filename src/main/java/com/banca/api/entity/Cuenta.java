package com.banca.api.entity;

import com.banca.api.core.base.BaseEntity;
import com.banca.api.core.utils.Constants;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(schema = Constants.DB_SCHEMA_API, name = "cuenta")
public class Cuenta extends BaseEntity<Cuenta,Long> {
    @Column(name="numero_cuenta")
    private String numeroCuenta;

    @Column(name="tipo_cuenta")
    private String typoCuenta;

    @Column(name="saldo_inicial")
    private Double saldoInicial;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "clienteid")
    private Cliente cliente;

    @OneToMany(mappedBy="cuenta")
    private Set<Movimientos> movimientos;


    public Cuenta() {
        super(Cuenta.class);
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTypoCuenta() {
        return typoCuenta;
    }

    public void setTypoCuenta(String typoCuenta) {
        this.typoCuenta = typoCuenta;
    }

    public Double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
    @JsonBackReference
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    @JsonManagedReference
    public Set<Movimientos> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Set<Movimientos> movimientos) {
        this.movimientos = movimientos;
    }
}
