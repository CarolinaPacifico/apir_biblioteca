package com.github.carolinapacifico.apir_biblioteca.repository;

import com.github.carolinapacifico.apir_biblioteca.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
