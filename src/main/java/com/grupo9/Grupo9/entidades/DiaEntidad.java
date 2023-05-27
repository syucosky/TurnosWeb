
package com.grupo9.Grupo9.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class DiaEntidad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    
    // Otras propiedades del día
    
    @OneToMany(mappedBy = "dia", cascade = CascadeType.ALL)
    private List<TurnosEntidad> turnos;
    
    // Constructor, getters y setters
}
