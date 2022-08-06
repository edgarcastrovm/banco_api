package com.banca.api.repo;

import com.banca.api.entity.Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientosRepo extends JpaRepository<Movimientos, Long>, JpaSpecificationExecutor<Movimientos> {

}
