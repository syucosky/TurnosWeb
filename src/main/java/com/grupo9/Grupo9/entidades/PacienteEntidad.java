package com.grupo9.Grupo9.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private Date fechaNacimiento;
    private String sexo;
    private String email;
    private String obraSocial;
    private Integer telefono;
    private String password;
    
    //LinkedList<HistorialClinico> historialClinico;
    
    public PacienteEntidad(){
    }

    public PacienteEntidad(Integer dni, String nombre, String apellido, Date fechaNacimiento, String sexo, String email, String obraSocial, Integer telefono, String password) {
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
