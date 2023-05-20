package com.grupo9.Grupo9.entidades;

import com.grupo9.Grupo9.enumeraciones.Rol;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    
    @Enumerated(EnumType.STRING)
    private Rol rol;
    
    private String tipoAtencion;
    private String ubicacion;
    
    private Boolean alta = false;
    
    @OneToMany(mappedBy="profesionales")
    private Set<EspecialidadEntidad> especialidad;    
    
    
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

    public ProfesionalEntidad(int dni, String nombre, String email, String password, String tipoAtencion, String ubicacion, Rol rol, List<ObraSocialEntidad> obraSocial) {
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.tipoAtencion = tipoAtencion;
        this.ubicacion = ubicacion;
        this.rol = rol;
        this.obraSocial = obraSocial;
    }
    
}

