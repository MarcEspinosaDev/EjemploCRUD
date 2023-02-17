package com.EjemploCRUD.CRUD.dao;

import com.EjemploCRUD.CRUD.entity.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    Optional<Direccion> findByCalle(String calle);
    boolean existsByCalle(String calle);
}
