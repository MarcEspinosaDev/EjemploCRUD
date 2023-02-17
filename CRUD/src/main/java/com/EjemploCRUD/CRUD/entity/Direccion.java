package com.EjemploCRUD.CRUD.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "direccion")
@Getter
@Setter
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column (name = "calle")
    private String calle;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private Restaurante restaurante;

    public Direccion(String calle) {
        this.calle = calle;
    }

    public Direccion() {
    }
}
