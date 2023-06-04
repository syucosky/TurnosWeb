
package com.grupo9.Grupo9.servicios;

import com.grupo9.Grupo9.entidades.PacienteEntidad;
import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
import com.grupo9.Grupo9.repositorios.PacienteRepositorio;
import com.grupo9.Grupo9.repositorios.ProfesionalRepository;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio  implements UserDetailsService {
    
    @Autowired
    ProfesionalRepository profesionalRepository;
    
    @Autowired
    PacienteRepositorio pacienteRepositorio;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        PacienteEntidad paciente = pacienteRepositorio.findByEmail(email);
        ProfesionalEntidad profesional = profesionalRepository.findByEmail(email);
        if (paciente != null){
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+paciente.getRol().toString());
            permisos.add(p);
            
            User user = new User(paciente.getEmail(), paciente.getPassword(),permisos);
            return user;
        }else if(profesional != null){
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+profesional.getRol().toString());
            permisos.add(p);
            
            User user = new User(profesional.getEmail(), profesional.getPassword(),permisos);
            return user;
        }else{
            return null;
        }
    }   
    
}
