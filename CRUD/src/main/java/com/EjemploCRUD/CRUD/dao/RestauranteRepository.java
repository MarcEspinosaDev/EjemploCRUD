package com.EjemploCRUD.CRUD.dao;

import com.EjemploCRUD.CRUD.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Repository
@CrossOrigin
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    Optional<Restaurante> findByNombre(String nombre);
    boolean existsByNombre(String nombre);

    //Condiciones de igualdad
    List<Restaurante> findByNombreIsNot(String nombre);
    List<Restaurante> findByNombreIs(String nombre);
    // Si es null
    List<Restaurante> findByNombreIsNull();
    List<Restaurante> findByNombreIsNotNull();
    //Condiciones que contienen
    List<Restaurante> findByNombreStartingWith(String prefijo);
    List<Restaurante> findByNombreEndingWith(String sufijo);
    List<Restaurante> findByNombreContaining(String nombre);
    List<Restaurante> findByNombreLike(String pattern);
    // condiciones de Comparacion (> que o < que)
    List<Restaurante> findByIdLessThan(Long id);
    List<Restaurante> findByIdLessThanEqual(Long id);
    List<Restaurante> findByIdGreaterThan(Long id);
    List<Restaurante> findByIdGreaterThanEqual(Long id);
    List<Restaurante> findByIdBetween(Long id, Long id2);
    //Condiciones de orden
    List<Restaurante> findAllByNombreOrderByNombre(String nombre);
    List<Restaurante> findAllByNombreOrderByNombreAsc(String nombre);
    List<Restaurante> findAllByNombreOrderByNombreDesc(String nombre);
    //Condiciones anidadas
    List<Restaurante> findByNombreAndDescripcion(String nombre, String descripcion);
    List<Restaurante> findByNombreOrDescripcion(String nombre, String descripcion);
    //Consultas con QUERYS por defecto(JPQL)
    /*
    @Query("select r from Restaurante r where r.nombre = Aldi")
    // se puede poner el nombre que quieras
    List<Restaurante> buscarAldi(); */

    @Query("select r from Restaurante r where r.nombre = ?1")
    List<Restaurante> buscarRestaurante(String nombre);
}
