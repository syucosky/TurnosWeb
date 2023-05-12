package com.grupo9.Grupo9.entidades;

import com.grupo9.Grupo9.repositorios.ProfesionalRepository;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ProfesionalEntidad implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String especialidad;
    private String tipoAtencion;
    private String ubicacion;
    
    @ManyToMany
    private List<ObraSocialEntidad> obraSocial;
    
    @ManyToMany
    private List<TurnosEntidad> horarios;
    
    private int puntosRecibidos; // SUMA DE LOS PUNTOS RECIBIDOS
    private int cantidadDeCalificaciones; // SUMA DE CADA PACIENTE QUE VOTO
    private int calificacion;  // PUNTAJE FINAL DEL PROFESIONAL
}
