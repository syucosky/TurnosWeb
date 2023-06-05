package com.grupo9.Grupo9.entidades;

import com.grupo9.Grupo9.enumeraciones.Rol;
import com.grupo9.Grupo9.validadores.Profesional.dni.DniUniqueConstraint;
import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Entity
@Table(name = "profesional")
public class ProfesionalEntidad implements Serializable {

    @Id
    @NotNull(message = "No puede estar vacío.")
    //@DniUniqueConstraint()
    private Integer dni;
    
    @NotEmpty(message = "No puede estar vacío.")
    private String nombre;
    
    @NotEmpty(message = "No puede estar vacío.")
    private String apellido;
    
    @NotEmpty(message = "No puede estar vacío.")
    private String email;

    private String password;
    
    @NotEmpty(message = "No puede estar vacío.")
    private String telefono;
    
    @NotEmpty(message = "No puede estar vacío.")
    private String sexo;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    private String tipoAtencion;
    private String ubicacion;

    @ManyToOne
    @JoinColumn(name = "especialidad_id")
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

    @ManyToMany
    @JoinTable(
            name = "turno_profesional",
            joinColumns = @JoinColumn(name = "profesional_id"),
            inverseJoinColumns = @JoinColumn(name = "turno_id")
    )
    private List<TurnosEntidad> turnos;

    private int puntosRecibidos; // SUMA DE LOS PUNTOS RECIBIDOS
    private int cantidadDeCalificaciones; // SUMA DE CADA PACIENTE QUE VOTO
    private int calificacion;  // PUNTAJE FINAL DEL PROFESIONAL

    @OneToOne
    @JoinColumn(name = "imagen_id", referencedColumnName = "id")
    private ImagenEntidad imagen;
    private int precioConsulta;

    public ProfesionalEntidad() {
    }

    public ProfesionalEntidad(Integer dni, String nombre, String email,
            String password, String apellido,
            String sexo, String ubicacion, String tipoAtencion, String telefono) {
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.apellido = apellido;
        this.sexo = sexo;
        this.ubicacion = ubicacion;
        this.tipoAtencion = tipoAtencion;
        this.rol = Rol.PROFESIONALNOAPTO;
        this.telefono = telefono;

    }

}
