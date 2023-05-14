package com.grupo9.Grupo9.entidades;

import com.grupo9.Grupo9.repositorios.ProfesionalRepository;
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
    private Long id;
    private String nombre;
    private String especialidad;
    private String tipoAtencion;
    private String ubicacion;
    
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
}
