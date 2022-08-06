package com.banca.api.core.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity <E, T> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private T id;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Transient
    @JsonIgnore
    protected final Logger LOG;

    public BaseEntity(Class<E> clazz) {
        LOG = LoggerFactory.getLogger(clazz);
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @PrePersist
    protected void onCreate() {
        if (estado == null) {
            estado = true;
        }
    }

}
