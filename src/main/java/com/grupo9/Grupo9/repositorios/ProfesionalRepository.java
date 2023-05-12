package com.grupo9.Grupo9.repositorios;

import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProfesionalRepository extends JpaRepository<ProfesionalEntidad, Long>{
    
} 
