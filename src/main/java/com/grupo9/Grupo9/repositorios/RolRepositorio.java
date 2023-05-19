package com.grupo9.Grupo9.repositorios;

import com.grupo9.Grupo9.entidades.RolEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepositorio extends JpaRepository<RolEntidad, Integer>{
    
    @Query("SELECT r FROM RolEntidad r WHERE r.nombre = :nombre")
    RolEntidad buscarRol(@Param("nombre") String nombre);
}
