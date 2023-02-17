package com.EjemploCRUD.CRUD.controller;

import com.EjemploCRUD.CRUD.dto.Mensaje;
import com.EjemploCRUD.CRUD.dto.RestauranteDto;
import com.EjemploCRUD.CRUD.entity.Categoria;
import com.EjemploCRUD.CRUD.entity.Direccion;
import com.EjemploCRUD.CRUD.entity.Imagen;
import com.EjemploCRUD.CRUD.entity.Restaurante;
import com.EjemploCRUD.CRUD.service.CategoriaService;
import com.EjemploCRUD.CRUD.service.DireccionService;
import com.EjemploCRUD.CRUD.service.RestauranteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/restaurante")
@CrossOrigin
public class RestauranteController {
    //CRUD
    @Autowired
    RestauranteService restauranteService;
    @Autowired
    CategoriaService categoriaService;
    @Autowired
    DireccionService direccionService;

    // READ
    @GetMapping("/lista")
    public ResponseEntity<List<Restaurante>> list(){
        List<Restaurante> list = restauranteService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    //Paginacion ordenada
    @GetMapping(value = "/list/order/", params = {"page","size","sorted"})
    public ResponseEntity<Page<Restaurante>> listSortBy(@RequestParam("page") int page,
                                                         @RequestParam("size")int size,
                                                         @RequestParam("sorted")String sorted){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sorted));
        Page<Restaurante> list = restauranteService.listPage(pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    //PAGINACION
    @GetMapping("/list/page={page}&size)={size}")
    public ResponseEntity<?> getById(@PathVariable("page")int page, @PathVariable("size")int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Restaurante> list = restauranteService.listPage(pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    // READ by ID
    @GetMapping("/detail/{id}")
    public ResponseEntity<Restaurante> getById(@PathVariable("id")long id){
        //si no existe pasa por el if
        if(!restauranteService.existsById(id))return new ResponseEntity(new Mensaje("don't exist"), HttpStatus.NOT_FOUND);
        // otro if para poner el "isPresent()", pero ya estamos seguros de que existe.
        if(restauranteService.getOne(id).isPresent()){
            Restaurante restaurante = restauranteService.getOne(id).get();
            return new ResponseEntity<>(restaurante, HttpStatus.OK);
        }
        // devolvemos una response aunque no vaya a llegar
        return new ResponseEntity(new Mensaje("Don't exist"), HttpStatus.NOT_FOUND);
    }

    // READ by NOMBRE
    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Restaurante> getByNombre(@PathVariable("nombre")String nombre){
        if(!restauranteService.existsByNombre(nombre)) return new ResponseEntity(new Mensaje("don't exist"), HttpStatus.NOT_FOUND);
        if(restauranteService.getByNombre(nombre).isPresent()){
            Restaurante restaurante = restauranteService.getByNombre(nombre).get();
            return new ResponseEntity<>(restaurante, HttpStatus.OK);
        }
        return new ResponseEntity(new Mensaje("don't exist"), HttpStatus.NOT_FOUND);
    }

    // POST
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody RestauranteDto restauranteDto){
        if(StringUtils.isBlank(restauranteDto.getNombre()))
            return new ResponseEntity<>(new Mensaje("nombre is required"),HttpStatus.BAD_REQUEST);
        if (restauranteService.existsByNombre(restauranteDto.getNombre()))
            return new ResponseEntity<>(new Mensaje("nombre already exist"),HttpStatus.BAD_REQUEST);

        Categoria categoria;
        Restaurante restaurante = new Restaurante(restauranteDto.getNombre(), restauranteDto.getDescripcion());

        //CATEGORIA
        if (categoriaService.getByCategoria(restauranteDto.getCategoria().getCategoria()).isPresent()){
            categoria = categoriaService.getByCategoria(restauranteDto.getCategoria().getCategoria()).get();
            restaurante.setCategoria(categoria);
        } else {
            if (StringUtils.isBlank(restauranteDto.getCategoria().getCategoria()))
                return new ResponseEntity<>(new Mensaje("no hay categoria"), HttpStatus.BAD_REQUEST);
            Categoria categoriaNew = restauranteDto.getCategoria();
            restaurante.setCategoria(categoriaNew);
            if (categoriaNew.getRestaurantes() != null){
                categoriaNew.getRestaurantes().add(restaurante);
            } else {
                Set<Restaurante> restaurantes = new HashSet<Restaurante>();
                restaurantes.add(restaurante);
                categoriaNew.setRestaurantes(restaurantes);
            }

        }
        //IMAGEN
        Set<Imagen> imagenes = restauranteDto.getImagenes();
        for (Imagen imagen: imagenes){
            imagen.setRestaurante(restaurante);
        }
        restaurante.setImagen(imagenes);
        //DIRECCION
        if (direccionService.existsByCalle(restauranteDto.getDireccion().getCalle())){
            return new ResponseEntity<>(new Mensaje("la direccion ya existe"), HttpStatus.BAD_REQUEST);
        } else {
            Direccion direccion = restauranteDto.getDireccion();
            restaurante.setDireccion(direccion);
            direccion.setRestaurante(restaurante);
        }

        restauranteService.save(restaurante);
        return new ResponseEntity<>(new Mensaje("Restaurante created"), HttpStatus.OK);
    }

    //UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody RestauranteDto restauranteDto){
        if (!restauranteService.existsById(id))
            return new ResponseEntity<>(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        if (restauranteService.existsByNombre(restauranteDto.getNombre()) && restauranteService.getByNombre(restauranteDto.getNombre()).get().getId() != id)
            return new ResponseEntity<>(new Mensaje("el nombre ya existe"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(restauranteDto.getNombre()))
            return new ResponseEntity<>(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if (direccionService.existsByCalle(restauranteDto.getDireccion().getCalle()))
            return new ResponseEntity<>(new Mensaje("la direccion ya existe"), HttpStatus.BAD_REQUEST);

        // hechas las comprobaciones modificamos el objeto
        Restaurante restaurante = restauranteService.getOne(id).get();
        restaurante.setNombre(restauranteDto.getNombre());
        restaurante.setDescripcion(restauranteDto.getDescripcion());
        restaurante.setCategoria(restauranteDto.getCategoria());
        restaurante.setDireccion(restauranteDto.getDireccion());
        restaurante.setImagen(restauranteDto.getImagenes());

        //lo ponemos en la bd y damos el ok
        restauranteService.save(restaurante);
        return new ResponseEntity<>(new Mensaje("restaurante actualizado"), HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        if (!restauranteService.existsById(id))
            return new ResponseEntity<>(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        restauranteService.delete(id);
        return new ResponseEntity<>(new Mensaje("restaurante eliminado"), HttpStatus.OK);
    }
}
