package com.grupo9.Grupo9.entidades;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class HistorialClinicoEntidad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private Integer edad;
    
    @Column(nullable = false)
    private String grupoSanguineo;
    
    @Column(nullable = false)
    private Double peso;
    
    @Column(nullable = false)
    private Integer altura;
    
    @Column(nullable = false)
    private String enfermedades;
    private LocalDate ultimoChequeo;
    @OneToOne
   private PacienteEntidad paciente;
    
    @Column(nullable = true)
    private String nombre;
     
    public HistorialClinicoEntidad() {
    }

    public HistorialClinicoEntidad(Integer edad, String nombre, String grupoSanguineo, Double peso, Integer altura, String enfermedades, LocalDate ultimoChequeo) {
        this.edad = edad;
        this.grupoSanguineo = grupoSanguineo;
        this.peso = peso;
        this.altura = altura;
        this.enfermedades = enfermedades;
        this.ultimoChequeo = ultimoChequeo;
        this.nombre = nombre;
    }

   
    
}
