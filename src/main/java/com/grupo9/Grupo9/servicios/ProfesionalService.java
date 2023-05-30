
package com.grupo9.Grupo9.servicios;

import com.grupo9.Grupo9.entidades.EspecialidadEntidad;
import com.grupo9.Grupo9.entidades.PacienteEntidad;
import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
import com.grupo9.Grupo9.entidades.TurnosEntidad;
import com.grupo9.Grupo9.enumeraciones.Rol;
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
public class ProfesionalService{
    @Autowired
    ProfesionalRepository profesionalRepositorio;
    
    @Transactional
    public void guardarProfesional(ProfesionalEntidad profesional, Boolean ok){
        try {
            if(ok){
                String passCod = profesional.getPassword();
                profesional.setPassword(new BCryptPasswordEncoder().encode(passCod));
                profesionalRepositorio.save(profesional);
            }else{
                profesionalRepositorio.save(profesional);
            }
           
        } catch (Exception e) {
            throw e; 
        }
    }
    
    public PacienteEntidad buscarPaciente(Integer dni){
        return profesionalRepositorio.buscarPaciente(dni);
    }

    public ProfesionalEntidad buscarPorEmail(String email){
        return profesionalRepositorio.findByEmail(email);
    }
    public void dardeAlta(Integer dni){
        profesionalRepositorio.altaProfesional(dni, Rol.PROFESIONALAPTO);
    }
    public void dardeBaja(Integer dni){
        profesionalRepositorio.altaProfesional(dni, Rol.PROFESIONALNOAPTO);
    }
    public List<ProfesionalEntidad> listarProfesionales(){
        
        return profesionalRepositorio.findAll();
    }
    
    public void setEspecialidad(Integer espeId, Integer dni){
        profesionalRepositorio.setEspecialidad(espeId, dni);
    }
    public ProfesionalEntidad obtenerProfesionalPorId(Integer dni) {
        return profesionalRepositorio.findById(dni).get();
    }
}
