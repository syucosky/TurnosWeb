
package com.grupo9.Grupo9.entidades;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "obra_social")
public class ObraSocialEntidad implements Serializable{
    
    @Id
    private long id;
    
    private String nombre;
    
    @ManyToMany(mappedBy = "obraSocial", cascade = CascadeType.ALL)
    private List<ProfesionalEntidad> profesionales;
    
  
    
    @OneToMany(mappedBy="obraSocial")
    private Set<PacienteEntidad> pacientes;
    
      @Override
    public String toString() {
        return this.nombre;
    
    }
}
