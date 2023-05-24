package com.grupo9.Grupo9.entidades;


//import java.io.Serializable;
//import java.util.Date;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "turno")
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
    
    @OneToOne
    @JoinColumn(nullable = false)
    private ProfesionalEntidad profesional;
    
    @ManyToOne
    @JoinColumn(name="especialidad_id",insertable = false, updatable = false)
    private EspecialidadEntidad especialidades;
    @Column(name="especialidad_id",nullable=false)
    private Integer id_especialidad;
    
    @ManyToOne
    @JoinColumn(name = "paciente_dni",insertable = false, updatable = false)
    private PacienteEntidad paciente;
    @Column(name = "paciente_dni", nullable = false)
    private Integer dni_paciente;

    public TurnosEntidad() {
    }
}
