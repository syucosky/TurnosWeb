package com.grupo9.Grupo9.repositorios;

import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
<<<<<<< Updated upstream
public interface ProfesionalRepository extends JpaRepository<ProfesionalEntidad, Long>{
=======
public interface ProfesionalRepository extends JpaRepository<ProfesionalEntidad, Integer>{
//    Optional<ProfesionalEntidad> findByCorreo(String email);
//
//    boolean existsProfesionalByEmail(String email);

    @Modifying
    @Query("UPDATE ProfesionalEntidad SET alta = :alta WHERE dni = :id")
    public void baja(@Param("id") Integer id, @Param("alta") Boolean alta);

    public Object findByEmail(String email);
>>>>>>> Stashed changes
    
} 
