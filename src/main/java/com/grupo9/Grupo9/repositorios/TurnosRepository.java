
package com.grupo9.Grupo9.repositorios;

import com.grupo9.Grupo9.entidades.TurnosEntidad;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnosRepository extends JpaRepository<TurnosEntidad,Integer> {
    @Modifying
    @Query("UPDATE TurnosEntidad SET alta = :alta WHERE id = :id")
    public void baja(@Param("id") Integer id, @Param("alta") Boolean alta);
   
    
    @Modifying
    @Query("UPDATE TurnosEntidad SET fecha = :fecha, hora = :hora, medico = :medico, lugar = :lugar WHERE id = :id")
    public void modificar(@Param("id") Integer id, @Param("fecha") LocalDate fecha, @Param("hora") LocalTime hora, @Param("medico") String medico, @Param("lugar") String lugar);
    
    @Query("SELECT t FROM TurnosEntidad t  WHERE paciente_dni = :paciente_dni")
    public TurnosEntidad obtenerTurnosCliente(@Param("paciente_dni") Integer paciente_dni);
}