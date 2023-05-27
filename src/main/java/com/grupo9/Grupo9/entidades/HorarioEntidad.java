
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

@Setter
@Getter
@Entity
public class HorarioEntidad implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String horario;
    // Otras propiedades del horario
    
    @OneToMany(mappedBy = "horario", cascade = CascadeType.ALL)
    private List<TurnosEntidad> turnos;
    
}
