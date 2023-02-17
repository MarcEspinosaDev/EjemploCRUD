package com.EjemploCRUD.CRUD.dao;

import com.EjemploCRUD.CRUD.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByCategoria(String categoria);
    boolean existsByCategoria(String categoria);
}
