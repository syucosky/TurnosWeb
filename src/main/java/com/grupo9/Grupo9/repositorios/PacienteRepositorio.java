package com.grupo9.Grupo9.repositorios;

import com.grupo9.Grupo9.entidades.PacienteEntidad;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepositorio extends JpaRepository<PacienteEntidad, Integer>{
       
    @Query("SELECT p FROM PacienteEntidad p WHERE p.email = :email")
    public Optional<PacienteEntidad> findByEmail(@Param("email") String email);
    
}
