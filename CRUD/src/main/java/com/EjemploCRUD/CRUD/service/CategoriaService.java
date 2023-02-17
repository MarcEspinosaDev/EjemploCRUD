package com.EjemploCRUD.CRUD.service;

import com.EjemploCRUD.CRUD.dao.CategoriaRepository;
import com.EjemploCRUD.CRUD.entity.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Optional<Categoria> getByCategoria(String categoria){ return categoriaRepository.findByCategoria(categoria); }
    public boolean existsByCategoria(String categoria){ return categoriaRepository.existsByCategoria(categoria); }
    public List<Categoria> list() { return categoriaRepository.findAll(); }
    public Optional<Categoria> getOne(long id) { return categoriaRepository.findById(id); }
    public void save(Categoria categoria) { categoriaRepository.save(categoria); }
    public void delete(long id){ categoriaRepository.deleteById(id); }
}
