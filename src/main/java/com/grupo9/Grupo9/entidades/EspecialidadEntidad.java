package com.grupo9.Grupo9.entidades;

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
public class EspecialidadEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nombre;
    
    private Boolean alta;
    
    @OneToMany(mappedBy="especialidad")
    private Set<ProfesionalEntidad> profesionales; 
    
    @Column(name = "profesional_id", nullable = false)
    private int profesional_dni;
    
    @OneToMany(mappedBy="especialidades")
    private Set<TurnosEntidad> turnos;
    

    
    public EspecialidadEntidad() {
    }

    public EspecialidadEntidad(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }  
    
}
