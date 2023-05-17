
package com.grupo9.Grupo9.controller;

import com.grupo9.Grupo9.entidades.ObraSocialEntidad;
import com.grupo9.Grupo9.entidades.PacienteEntidad;
import com.grupo9.Grupo9.servicios.ObraSocialService;
import com.grupo9.Grupo9.servicios.PacienteServicio;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        return "index.html";
    }
    @GetMapping("/registrar")
    public String registro(){    
        return "registro.html";
    }
      @Autowired
    PacienteServicio pacienteServicio;
    @Autowired
    ObraSocialService obraSocialServicio;
    @GetMapping("/Modificar/{id}")
    public String modificar(@PathVariable("dni") Integer dni){
        
        return "modificar.html";
    }
    @PostMapping("/registrar/paciente")
    public String registrarPaciente(
                                    @RequestParam("dni") Integer dni,
                                    @RequestParam("nombre") String nombre,
                                    @RequestParam("apellido") String apellido,
                                    @RequestParam("email") String email,
                                    @RequestParam(value = "obras") String obras,
                                    @RequestParam("fNacimiento") Date fNacimiento,
                                    @RequestParam("telefono") Integer telefono,
                                    @RequestParam("sexo") String sexo,
                                    @RequestParam("password") String password){
        try {
            ObraSocialEntidad obraSocial = obraSocialServicio.buscarPorNombre(obras);
            PacienteEntidad paciente = new PacienteEntidad(dni, nombre, apellido, fNacimiento, sexo, email, obraSocial, telefono, password);
            pacienteServicio.guardarPaciente(paciente);
        } catch (Exception e) {
        }   
        return "redirect:/paciente";
    }

    
}
