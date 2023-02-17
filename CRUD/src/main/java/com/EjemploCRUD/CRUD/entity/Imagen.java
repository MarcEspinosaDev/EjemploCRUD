package com.EjemploCRUD.CRUD.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "imagen")
@Getter
@Setter
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column (name = "imagen")
    private String imagen;

    @ManyToOne (cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    public Imagen(String imagen) {
        this.imagen = imagen;
    }

    public Imagen() {
    }
}
