package com.grupo9.Grupo9.entidades;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "turno")
public class TurnosEntidad implements Serializable{
    
    @Id
    private Long id;
    
    private String dia;
    private Date horario; // FALTA DEFINIR EL TIPO DATE PARA SABER COMO MANEJAR LOS HORARIOS 
    
    @OneToOne
    private ProfesionalEntidad profesional;
}
