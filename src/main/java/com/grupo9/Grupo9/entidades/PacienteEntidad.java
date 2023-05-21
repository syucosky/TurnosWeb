package com.grupo9.Grupo9.entidades;

import java.util.Date;
import javax.persistence.Entity;
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

@Getter
@Setter
@Entity
@Table(name = "paciente")
public class PacienteEntidad {
    
    @Id
    private Integer dni;
    
    private String nombre;
    private String apellido;   
    private String fechaNacimiento;
    private String sexo;
    private String email;
    
    @ManyToOne
    @JoinColumn(name="obraS_id")
    private ObraSocialEntidad obraSocial;
    
    private Integer telefono;
    private String password;
    
    @OneToOne
    private HistorialClinicoEntidad historialClinico;
    
    public PacienteEntidad(){
    }

    public PacienteEntidad(Integer dni, String nombre, String apellido, String fechaNacimiento, String sexo, String email, ObraSocialEntidad obraSocial, Integer telefono, String password) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.email = email;
        this.obraSocial = obraSocial;
        this.telefono = telefono;
        this.password = password;
    }
    
    
    
    
}
