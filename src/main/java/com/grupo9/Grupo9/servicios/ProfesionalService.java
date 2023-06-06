package com.grupo9.Grupo9.servicios;

import com.grupo9.Grupo9.entidades.EspecialidadEntidad;
import com.grupo9.Grupo9.entidades.PacienteEntidad;
import com.grupo9.Grupo9.entidades.ImagenEntidad;
import com.grupo9.Grupo9.entidades.ObraSocialEntidad;
import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
import com.grupo9.Grupo9.entidades.TurnosEntidad;
import com.grupo9.Grupo9.enumeraciones.Rol;
import com.grupo9.Grupo9.repositorios.ImagenRepository;
import com.grupo9.Grupo9.excepciones.MiExcepcion;
import com.grupo9.Grupo9.repositorios.ProfesionalRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfesionalService {

    @Autowired
    ProfesionalRepository profesionalRepositorio;

    @Autowired
    ObraSocialService obrasServicio;
    @Autowired
    ImagenRepository imagenRepository;

    @Transactional
    public void guardarProfesional(ProfesionalEntidad profesional, Boolean ok, Long[] obrasSocialesId) {
        try {

            if (obrasSocialesId != null) {

                // ELIMINO TODAS LAS OBRAS SOCIALES DEL PROFESIONAL
                // PARA LUEGO AGREGAR O ELIMINAR LAS DE UI
                profesional.getObraSocial().clear();
                for (Long obraSocialId : obrasSocialesId) {
                    ObraSocialEntidad oSocial = obrasServicio.buscarPorId(obraSocialId);
                    profesional.getObraSocial().add(oSocial);
                }
            }

            if (ok) {
                String passCod = profesional.getPassword();
                profesional.setPassword(new BCryptPasswordEncoder().encode(passCod));
                profesionalRepositorio.save(profesional);
            } else {
                profesionalRepositorio.save(profesional);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public PacienteEntidad buscarPaciente(Integer dni) {
        return profesionalRepositorio.buscarPaciente(dni);
    }

    //@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ProfesionalEntidad profesional = profesionalRepositorio.findByEmail(email);
        if (profesional != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + profesional.getRol().toString());
            permisos.add(p);
            User user = new User(profesional.getEmail(), profesional.getPassword(), permisos);
            return user;
        } else {
            return null;
        }
    }

    public ProfesionalEntidad buscarPorEmail(String email) {
        return profesionalRepositorio.findByEmail(email);
    }

    public void dardeAlta(Integer dni) {
        profesionalRepositorio.altaProfesional(dni, Rol.PROFESIONALAPTO);
    }

    public void dardeBaja(Integer dni) {
        profesionalRepositorio.altaProfesional(dni, Rol.PROFESIONALNOAPTO);
    }

    public List<ProfesionalEntidad> listarProfesionales() {

        return profesionalRepositorio.findAll();
    }

    public void setEspecialidad(Integer espeId, Integer dni) {
        profesionalRepositorio.setEspecialidad(espeId, dni);
    }

    public ProfesionalEntidad obtenerProfesionalPorId(Integer dni) {
        return profesionalRepositorio.findById(dni).get();
    }

    public ProfesionalEntidad buscarPorDni(Integer dni) {
        return profesionalRepositorio.findById(dni).orElse(null);
    }
        /*DELETE*/
    public List<ProfesionalEntidad> buscarProfesionalesEspecialidad(String especialidad) {
        return (List<ProfesionalEntidad>) profesionalRepositorio.getProfesionalesByEspecialidad(especialidad);
    }

    public List<ProfesionalEntidad> buscarProfesionalesPorObraSocial(String obraSocial) {
        return profesionalRepositorio.getProfesionalesByObraSocial(obraSocial);
    }

}
