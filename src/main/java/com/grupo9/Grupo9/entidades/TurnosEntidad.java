package com.grupo9.Grupo9.entidades;


//import java.io.Serializable;
//import java.util.Date;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.OneToOne;
import java.io.Serializable;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "turno")
public class TurnosEntidad implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;   
    
    @ManyToMany
    @JoinTable(
        name = "turno_dia_horario",
        joinColumns = @JoinColumn(name = "turno_id"),
        inverseJoinColumns = @JoinColumn(name = "dia_horario_id")
    )
    private Set<DiaHorario> diasHorarios = new HashSet<>();

    


    public TurnosEntidad() {
    }
}
