package com.grupo9.Grupo9.repositorios;

import com.grupo9.Grupo9.entidades.PacienteEntidad;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepositorio extends JpaRepository<PacienteEntidad, Integer>{
    
    long countByDni(Integer dni);
    
    boolean existsPacienteEmail(String email);
    
    @Query("SELECT p FROM PACIENTE p WHERE p.dni = :dni")
    public Optional<PacienteEntidad> obtenerPerfil(@Param("dni") Integer dni);
    
}
