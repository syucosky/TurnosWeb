package com.grupo9.Grupo9.repositorios;

import com.grupo9.Grupo9.entidades.EspecialidadEntidad;
import com.grupo9.Grupo9.entidades.PacienteEntidad;
import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
import com.grupo9.Grupo9.entidades.TurnosEntidad;
import com.grupo9.Grupo9.enumeraciones.Rol;
import java.util.List;
import java.util.Optional;
//import static javafx.scene.input.KeyCode.T;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesionalRepository extends JpaRepository<ProfesionalEntidad, Integer> {

    @Query("SELECT p FROM PacienteEntidad p WHERE p.profesionalId = :dni")
    public PacienteEntidad buscarPaciente(@Param("dni") Integer dni);

    @Query("SELECT p FROM ProfesionalEntidad p WHERE p.email = :email")
    public ProfesionalEntidad findByEmail(@Param("email") String email);

    @Modifying
    @Query("UPDATE ProfesionalEntidad SET rol = :rol WHERE dni = :dni")
    public ProfesionalEntidad altaProfesional(@Param("dni") Integer dni, @Param("rol") Rol rol);

    @Modifying
    @Query("UPDATE ProfesionalEntidad SET especialidad = :especialidad WHERE dni = :dni")
    public void setEspecialidad(@Param("especialidad") Integer espeId, @Param("dni") Integer dni);

    @Query("SELECT p FROM ProfesionalEntidad p WHERE p.especialidad.nombre = :especialidad")
    public List<ProfesionalEntidad> getProfesionalesByEspecialidad(@Param("especialidad") String especialidad);

    @Query("SELECT p FROM ProfesionalEntidad p LEFT JOIN fetch p.obraSocial os WHERE os.nombre = :obraSocial")
    public List<ProfesionalEntidad> getProfesionalesByObraSocial(@Param("obraSocial") String obraSocial);
}
