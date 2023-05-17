package com.grupo9.Grupo9.controller;

import com.grupo9.Grupo9.entidades.ObraSocialEntidad;
import com.grupo9.Grupo9.entidades.PacienteEntidad;
import com.grupo9.Grupo9.servicios.ObraSocialService;
import com.grupo9.Grupo9.servicios.PacienteServicio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/paciente")
public class PacienteController {
    
    @Autowired  
    PacienteServicio pacienteServicio;
    
    @GetMapping("")
    public String listarClientes(ModelMap model){
        List<PacienteEntidad> pacientes = pacienteServicio.todosLosPacientes();
        model.addAttribute("pacientes",pacientes);
        return "lista-cliente.html";
    }
    
    @DeleteMapping("/borrar/{dni}")
    public String borrarPorId(@PathVariable("dni") Integer dni){
        pacienteServicio.eliminar(dni);
        
        return "lista-cliente.html";
    }
}
