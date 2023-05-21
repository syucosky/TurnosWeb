
package com.grupo9.Grupo9.controller;

import com.grupo9.Grupo9.entidades.ObraSocialEntidad;
import com.grupo9.Grupo9.entidades.PacienteEntidad;
import com.grupo9.Grupo9.servicios.ObraSocialService;
import com.grupo9.Grupo9.servicios.PacienteServicio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/")
public class IndexController {
    
    @GetMapping("/")
    public String vista(){    
        return "login.html";
    }
    @GetMapping("/seleccion-usuario")
    public String seleccionUsuario(){    
        return "seleccion-usuario.html";
    }
    @GetMapping("/registro-paciente")
    public String registrarPaciente(ModelMap modelo){
        List<ObraSocialEntidad> obras = new ArrayList();
        obras = obraSocialServicio.buscarTodas();
        modelo.addAttribute("obras",obras); 
        return "registro-paciente.html";
    }
    @Autowired
    PacienteServicio pacienteServicio;
    @Autowired
    ObraSocialService obraSocialServicio;
    @GetMapping("/Modificar/{id}")
    public String modificar(@PathVariable("dni") Integer dni){
        
        return "modificar.html";
    }
    @PostMapping("/seleccion-usuario/registro-paciente")
    public String registrarPaciente(
                                    @RequestParam(value = "dni") Integer dni,
                                    @RequestParam(value = "nombre") String nombre,
                                    @RequestParam(value = "apellido") String apellido,
                                    @RequestParam(value = "email") String email,
                                    @RequestParam(value = "obraselec") String obraselec,
                                    @RequestParam(value = "fechaNac") String fNacimiento,
                                    @RequestParam(value = "telefono") Integer telefono,
                                    @RequestParam(value = "sexo") String sexo,
                                    @RequestParam(value = "password") String password){
        try {
            ObraSocialEntidad obraSocial = obraSocialServicio.buscarPorNombre(obraselec);
            PacienteEntidad paciente = new PacienteEntidad(dni, nombre, apellido, fNacimiento, sexo, email, obraSocial, telefono, password);
            pacienteServicio.guardarPaciente(paciente);
        } catch (Exception e) {
        }   
        return "redirect:/paciente";
    }

    
}
