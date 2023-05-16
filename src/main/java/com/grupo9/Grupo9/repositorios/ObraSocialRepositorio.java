package com.grupo9.Grupo9.repositorios;

import com.grupo9.Grupo9.entidades.ObraSocialEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ObraSocialRepositorio extends JpaRepository<ObraSocialEntidad, Long> {
    
    @Query("SELECT o FROM ObraSocialEntidad o WHERE o.nombre = :nombre")
    public ObraSocialEntidad findByName(@Param("nombre")String nombre);
}
