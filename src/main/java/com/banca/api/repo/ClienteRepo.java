package com.banca.api.repo;

import com.banca.api.entity.Cliente;
import com.banca.api.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepo extends JpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {

}
