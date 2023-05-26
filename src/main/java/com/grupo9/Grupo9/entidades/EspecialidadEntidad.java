package com.grupo9.Grupo9.entidades;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "especialidad")
public class EspecialidadEntidad  implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nombre;
    
    // Relación uno a muchos con Profesional
    @OneToMany(mappedBy = "especialidad")
    private List<ProfesionalEntidad> profesionales;
    
    @OneToMany(mappedBy="especialidades")
    private Set<TurnosEntidad> turnos;
    

    
    public EspecialidadEntidad() {
    }

    public EspecialidadEntidad(String nombre) {
        this.nombre = nombre;
    }  
    
}
