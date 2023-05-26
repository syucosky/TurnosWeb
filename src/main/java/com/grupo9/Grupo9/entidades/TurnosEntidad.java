package com.grupo9.Grupo9.entidades;


//import java.io.Serializable;
//import java.util.Date;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "turno")
public class TurnosEntidad{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;   
    
    @ManyToOne
    @JoinColumn(name = "profesional_id")
    private ProfesionalEntidad profesional;
   
    //@ManyToOne
//    @JoinColumn(name = "dia_id")
//    private DiaEntidad dia;
//    
//    @ManyToOne
//    @JoinColumn(name = "horario_id")
//    private HorarioEntidad horario;

    


    public TurnosEntidad() {
    }
}
