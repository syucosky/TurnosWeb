
package com.grupo9.Grupo9.repositorios;

import com.grupo9.Grupo9.entidades.TurnosEntidad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Repository
public interface TurnosRepository extends JpaRepository<TurnosEntidad,Integer> {
   
    @Query("SELECT t FROM TurnosEntidad t INNER JOIN t.profesionales ab WHERE ab.id = :profId")
    public List<TurnosEntidad> turnosIdProf(@Param("profId") Integer profId);
}

