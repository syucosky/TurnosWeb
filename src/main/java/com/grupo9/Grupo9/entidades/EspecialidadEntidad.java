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
public class EspecialidadEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 50)
    private String nombre;
    
    private Boolean alta;
    
    private Integer idProfesional;

    public EspecialidadEntidad() {
    }

    public EspecialidadEntidad(Integer id, String nombre, Boolean alta, Integer idProfesional) {
        this.id = id;
        this.nombre = nombre;
        this.alta = alta;
        this.idProfesional = idProfesional;
    }  
    
}
