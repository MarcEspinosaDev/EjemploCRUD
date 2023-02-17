package com.EjemploCRUD.CRUD.dao;

import com.EjemploCRUD.CRUD.entity.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenRepository extends JpaRepository<Imagen, Long> {
}
