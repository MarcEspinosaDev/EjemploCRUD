package com.EjemploCRUD.CRUD.service;

import com.EjemploCRUD.CRUD.dao.RestauranteRepository;
import com.EjemploCRUD.CRUD.entity.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RestauranteService {
    @Autowired
    RestauranteRepository restauranteRepository;

    // GET todos los restaurantes
    public List<Restaurante> list() {return restauranteRepository.findAll(); }
    // Paginacion -NEW- (Se puede sustituir "Page" : "Slice")
    public Page<Restaurante> listPage(Pageable pageable){return restauranteRepository.findAll(pageable);}
    // GET por id
    public Optional<Restaurante> getOne(long id) { return restauranteRepository.findById(id);}
    // GET por nombre
    public Optional<Restaurante> getByNombre(String nombre) {return  restauranteRepository.findByNombre(nombre);}
    // POST un restaurante
    public void save(Restaurante restaurante) { restauranteRepository.save(restaurante); }
    //DELETE un restaurante por id
    public void delete(long id) { restauranteRepository.findById(id); }
    // checkea si existe el id y el nombre
    public boolean existsById(long id) { return restauranteRepository.existsById(id); }
    public boolean existsByNombre(String nombre) { return restauranteRepository.existsByNombre(nombre); }

}
