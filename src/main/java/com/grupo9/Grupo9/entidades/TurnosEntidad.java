package com.grupo9.Grupo9.entidades;


//import java.io.Serializable;
//import java.util.Date;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TurnosEntidad{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false) //la base de datos no puede contener valores nulos
    private LocalDate fecha;
    
    @Column(nullable = false)
    private LocalTime hora;
    
    @Column(nullable = false)
    private String medico;
    
    @Column(nullable = false)
    private String lugar;
    
    @Column(nullable = false)
    private Boolean alta;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private EspecialidadEntidad especialidad;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private PacienteEntidad paciente;

    public TurnosEntidad() {
    }

    
}
