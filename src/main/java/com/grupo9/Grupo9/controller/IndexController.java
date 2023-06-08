package com.grupo9.Grupo9.controller;

import com.grupo9.Grupo9.entidades.Filtro;
import com.grupo9.Grupo9.entidades.PacienteEntidad;
import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
import com.grupo9.Grupo9.enumeraciones.Rol;
import com.grupo9.Grupo9.servicios.EspecialidadServicio;
import com.grupo9.Grupo9.servicios.ObraSocialService;
import com.grupo9.Grupo9.servicios.PacienteServicio;
import com.grupo9.Grupo9.servicios.ProfesionalService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("")
public class IndexController {

    @Autowired
    PacienteServicio pacienteServicio;
    @Autowired
    ObraSocialService obraSocialServicio;
    @Autowired
    ProfesionalService profesionalServicio;
    @Autowired
    EspecialidadServicio especialidadServicio;

    @GetMapping("")
    public String principio() {
        return "login.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Email o contraseña incorrecta");
        }
        return "login.html";
    }

    @GetMapping("/inicio")
    public String inicio(ModelMap modelo) {

        UserDetails currectUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //SI ES PACIENTE:
        if (currectUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PACIENTE"))) {
            List<ProfesionalEntidad> profesionales = profesionalServicio.listarProfesionales();
            modelo.addAttribute("profesionales", profesionales);
            modelo.addAttribute("filtro", new Filtro());
            return "inicio.html";

        }
        
        //SI ES PROFESIONAL:
        
        ProfesionalEntidad profesionalLogueado = profesionalServicio.buscarPorEmail(currectUser.getUsername());
        
        return "redirect:/profesional/perfil?dni="+profesionalLogueado.getDni();

    }

    @GetMapping("/seleccion-usuario")
    public String seleccionUsuario() {
        return "seleccion-usuario.html";
    }

}
