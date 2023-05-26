package com.grupo9.Grupo9.entidades;

import com.grupo9.Grupo9.enumeraciones.Rol;
import java.io.Serializable;
import java.util.ArrayList;
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
    private Integer dni;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;
    private String sexo;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    
    private String tipoAtencion;
    private String ubicacion;
    
       
    @ManyToOne
    @JoinColumn(name="especialida_id", insertable = false, updatable = false)
    private EspecialidadEntidad especialidad;
    
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
    private List<ObraSocialEntidad> obraSocial = new ArrayList<ObraSocialEntidad>();

    
    private int puntosRecibidos; // SUMA DE LOS PUNTOS RECIBIDOS
    private int cantidadDeCalificaciones; // SUMA DE CADA PACIENTE QUE VOTO
    private int calificacion;  // PUNTAJE FINAL DEL PROFESIONAL

    public ProfesionalEntidad() {
    }
                                                      
    public ProfesionalEntidad(Integer dni, String nombre, String email, 
                             String password, String apellido,
                             String sexo, String ubicacion, String tipoAtencion) {
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.apellido = apellido;
        this.sexo = sexo;   
        this.ubicacion = ubicacion;
        this.tipoAtencion = tipoAtencion;
        this.rol = Rol.PROFESIONALNOAPTO;
    }

    
}

