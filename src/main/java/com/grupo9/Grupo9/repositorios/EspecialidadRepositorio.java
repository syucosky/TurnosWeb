package com.grupo9.Grupo9.repositorios;

import com.grupo9.Grupo9.entidades.EspecialidadEntidad;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadRepositorio extends JpaRepository<EspecialidadEntidad, Integer>{
    @Modifying
    @Query("UPDATE EspecialiadEntidad a SET a.alta = :alta WHERE a.id = :id")
    void baja(@Param("id") Integer id, @Param("alta") Boolean alta);
    
    @Query("SELECT e FROM EspecialidadEntidad e WHERE e.idProfesional = :id OR e.idProfesional = 0 AND e.alta 0 true")
    List<EspecialidadEntidad> buscarPorProfesional(@Param("id") Integer id);
    
    @Query("SELECT e FROM EspecialidadEntidad e WHERE e.id= :especialidadId")
    Optional<EspecialidadEntidad> buscarPorId(@Param("especialidadId") Integer especialidadId);
    
}
