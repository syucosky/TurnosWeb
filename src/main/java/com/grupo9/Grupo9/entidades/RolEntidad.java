package com.grupo9.Grupo9.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RolEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nombre;

    public RolEntidad() {
    }

    public RolEntidad(String nombre) {
        this.nombre = nombre;
    }

    public RolEntidad(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
