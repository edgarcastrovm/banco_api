package com.banca.api.repo;

import com.banca.api.entity.DetalleMovimientoView;
import com.banca.api.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleMovimientoViewRepo extends JpaRepository<DetalleMovimientoView, Long>, JpaSpecificationExecutor<DetalleMovimientoView> {

}
