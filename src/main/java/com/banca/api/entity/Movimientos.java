package com.banca.api.entity;

import com.banca.api.core.base.BaseEntity;
import com.banca.api.core.utils.Constants;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = Constants.DB_SCHEMA_API, name = "movimientos")
public class Movimientos extends BaseEntity<Movimientos,Long> {
    @Column(name="fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name="tipo_movimiento")
    private String tipoMovimiento;

    @Column(name="valor")
    private Double valor;

    @Column(name="saldo")
    private Double saldo;

    @Column(name="saldo_inicial")
    private Double saldoInicial;

    @ManyToOne
    @JoinColumn(name = "id_cuenta")
    private Cuenta cuenta;

    public Movimientos() {
        super(Movimientos.class);
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    @JsonBackReference
    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
}
