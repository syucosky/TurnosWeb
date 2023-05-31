package com.grupo9.Grupo9.entidades;

import com.grupo9.Grupo9.enumeraciones.Rol;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Entity
@Table(name = "paciente")
public class PacienteEntidad implements Serializable {
    
    @Id
    private Integer dni;
    
    private String nombre;
    private String apellido;   
    private String fechaNacimiento;
    private String sexo;
    private String email;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    
    @ManyToOne
    @JoinColumn(name="obraS_id")
    private ObraSocialEntidad obraSocial;
    
    private String telefono;
    private String password;
    @JoinColumn(name = "turno_id")
    private Integer turnoId;
    @JoinColumn(name ="profesional_id")
    private Integer profesionalId;
            
    @OneToOne
    private HistorialClinicoEntidad historialClinico;
    
    public PacienteEntidad(){
    }

    public PacienteEntidad(Integer dni, String nombre, String apellido, String fechaNacimiento, String sexo, String email, ObraSocialEntidad obraSocial, String telefono, String password) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.email = email;
        this.obraSocial = obraSocial;
        this.telefono = telefono;
        this.password = password;
        this.rol = Rol.PACIENTE;
    }
    
     
    
}
