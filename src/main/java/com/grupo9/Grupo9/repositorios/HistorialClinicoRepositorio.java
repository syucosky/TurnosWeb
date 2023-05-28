package com.grupo9.Grupo9.repositorios;

import com.grupo9.Grupo9.entidades.HistorialClinicoEntidad;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialClinicoRepositorio extends JpaRepository<HistorialClinicoEntidad, Integer>{
    
    @Query("SELECT h FROM HistorialClinicoEntidad h WHERE h.paciente_dni = :dni")
    public Optional<HistorialClinicoEntidad> obtenerHistorialClinico(@Param("dni") Integer dni);
    
}
