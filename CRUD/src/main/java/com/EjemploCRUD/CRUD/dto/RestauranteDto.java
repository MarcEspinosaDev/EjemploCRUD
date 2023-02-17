package com.EjemploCRUD.CRUD.dto;

import com.EjemploCRUD.CRUD.entity.Categoria;
import com.EjemploCRUD.CRUD.entity.Direccion;
import com.EjemploCRUD.CRUD.entity.Imagen;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RestauranteDto {
    @NotBlank
    private String nombre;

    @NotBlank
    private String descripcion;

    private Categoria categoria;

    private Direccion direccion;

    private Set<Imagen> imagenes;

    public RestauranteDto(String nombre, String descripcion, Categoria categoria, Direccion direccion, Set<Imagen> imagenes) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.direccion = direccion;
        this.imagenes = imagenes;
    }

    public RestauranteDto() {
    }
}
