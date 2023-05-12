
package com.grupo9.Grupo9.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ObraSocialEntidad implements Serializable{
    
    @Id
    private long id;
    
    private String nombre;
    
    @ManyToMany
    private List<ProfesionalEntidad> profesionales;
    
}
