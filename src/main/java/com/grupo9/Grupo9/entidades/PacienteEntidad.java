package com.grupo9.Grupo9.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PacienteEntidad {
    
    @Id
    private Integer dni;
    
    private String nombre;
    private String apellido;
    
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    private String sexo;
    private String email;
    private String obraSocial;
    private Integer telefono;
    
    //LinkedList<HistorialClinico> historialClinico;
    
    public PacienteEntidad(){
    }

    public PacienteEntidad(Integer dni, String nombre, String apellido, Date fechaNacimiento, String sexo, String email, String obraSocial, Integer telefono) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.email = email;
        this.obraSocial = obraSocial;
        this.telefono = telefono;
    }
    
    
    
    
}
