package com.grupo9.Grupo9.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profesional")
public class ProfesionalEntidad implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dni;
    private String nombre;
    private String email;
    private String password;
    private EspecialidadEntidad especialidad;
    private String tipoAtencion;
    private String ubicacion;
    
    private Boolean alta;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private RolEntidad rol;
    
    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "profesional_osocial",
            joinColumns = @JoinColumn(name = "profesional_id"),
            inverseJoinColumns = @JoinColumn(name = "osocial_id")
    )
    private List<ObraSocialEntidad> obraSocial;

    
    private int puntosRecibidos; // SUMA DE LOS PUNTOS RECIBIDOS
    private int cantidadDeCalificaciones; // SUMA DE CADA PACIENTE QUE VOTO
    private int calificacion;  // PUNTAJE FINAL DEL PROFESIONAL

    public ProfesionalEntidad() {
    }

    public ProfesionalEntidad(int dni, String nombre, String email, String password, EspecialidadEntidad especialidad, String tipoAtencion, String ubicacion, RolEntidad rol, List<ObraSocialEntidad> obraSocial, int puntosRecibidos, int cantidadDeCalificaciones, int calificacion) {
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.especialidad = especialidad;
        this.tipoAtencion = tipoAtencion;
        this.ubicacion = ubicacion;
        this.rol = rol;
        this.obraSocial = obraSocial;
        this.puntosRecibidos = puntosRecibidos;
        this.cantidadDeCalificaciones = cantidadDeCalificaciones;
        this.calificacion = calificacion;
    }
    
}
