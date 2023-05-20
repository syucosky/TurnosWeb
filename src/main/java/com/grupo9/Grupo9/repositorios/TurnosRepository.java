
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
    @Query("UPDATE TurnosEntidad a SET a.alta = :alta WHERE a.id = :id")
    void baja(@Param("id") Integer id, @Param("alta") Boolean alta);
    
    @Query("SELECT t FROM TurnosEntidad t WHERE t.id = :id")
    TurnosEntidad buscarPorId(@Param("id") Integer id);
    
    @Modifying
    @Query("UPDATE TurnosEntidad t SET t.fecha = :fecha, t.hora = :hora, t.medico = :medico, t.lugar = :lugar WHERE t.id = :id")
    void modificar(@Param("id") Integer id, @Param("fecha") LocalDate fecha, @Param("hora") LocalTime hora, @Param("medico") String medico, @Param("lugar") String lugar);
    
    @Query("SELECT t FROM TurnosEntidad t WHERE t.paciente.dni = :paciente_dni")
    public List<TurnosEntidad> obtenerTurnosPaciente(@Param("paciente_dni") Integer paciente_dni);
    
    @Query("SELECT t FROM TurnosEntidad t WHERE t.paciente.dni = :paciente_dni")
    public Optional<TurnosEntidad> obtenerTurnosPaciente(@Param("paciente_dni") Integer paciente_dni, @Param("turnosId") Integer turnosId);
    
    @Query("SELECT t FROM TurnosEntidad t WHERE t.paciente.dni = : paciente_dni AND MONTH(t.fecha)=MONTH(:fechadeHoy) AND YEAR(t.fecha) =YEAR(:fechadeHoy) AND t.alta = true")
    public List<TurnosEntidad> obtenerTurnosMes(@Param("paciente_dni") Integer paciente_dni, @Param("fechadeHoy") LocalDate fechadeHoy);
    
    @Query("SELECT t FROM TurnosEntidad t WHERE t.paciente.dni = :paciente_dni AND (MONTH(t.fecha) != MONTH(:fechadeHoy) AND YEAR(t.fecha) !=YEAR(:fechadeHoy)) AND t.fecha > :fechadeHoy AND t.alta = true")
    public List<TurnosEntidad> obtenerTurnosFuturo(@Param("paciente_dni") Integer paciente_dni, @Param("fechadeHoy") LocalDate fechadeHoy);
}
