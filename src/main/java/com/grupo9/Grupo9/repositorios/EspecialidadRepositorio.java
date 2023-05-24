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
    
//    @Query("SELECT e FROM EspecialidadEntidad e WHERE e.profesional_id = :id")
//    public List<EspecialidadEntidad> buscarPorProfesional(@Param("id") Integer id);

    
 
}
