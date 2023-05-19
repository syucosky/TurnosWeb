package com.grupo9.Grupo9.repositorios;

import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProfesionalRepository extends JpaRepository<ProfesionalEntidad, Integer>{
    Optional<ProfesionalEntidad> findByCorreo(String email);

    boolean existsProfesionalByEmail(String email);

    @Modifying
    @Query("UPDATE Profesional a SET a.alta = :alta WHERE a.id = :id")
    void baja(@Param("id") Integer id, @Param("alta") Boolean alta);
} 
