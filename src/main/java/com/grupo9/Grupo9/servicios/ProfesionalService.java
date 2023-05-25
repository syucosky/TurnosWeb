
package com.grupo9.Grupo9.servicios;

import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
import com.grupo9.Grupo9.enumeraciones.Rol;
import com.grupo9.Grupo9.repositorios.ProfesionalRepository;
import java.util.ArrayList;
import java.util.List;
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
public class ProfesionalService implements UserDetailsService {
    @Autowired
    ProfesionalRepository profesionalRepositorio;
    
    @Transactional
    public void guardarProfesional(ProfesionalEntidad profesional){
        try {
            String passCod = profesional.getPassword();
            profesional.setPassword(new BCryptPasswordEncoder().encode(passCod));
            profesionalRepositorio.save(profesional);
        } catch (Exception e) {
            throw e; 
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ProfesionalEntidad profesional = profesionalRepositorio.findByEmail(email);
        if(profesional != null){
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+profesional.getRol().toString());
            permisos.add(p);
            User user = new User(profesional.getEmail(), profesional.getPassword(),permisos);
            return user;
        }else{
            return null;
        }
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
    
}
