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
    @Autowired
    ObraSocialService obrasServicio;
    
    @GetMapping("")
    public String listarClientes(ModelMap model){
        List<PacienteEntidad> pacientes = pacienteServicio.todosLosPacientes();
        model.addAttribute("pacientes",pacientes);
        return "lista-cliente.html";
    }
    
    @GetMapping("/borrar/{dni}")
    public String borrarPorId(@PathVariable("dni") Integer dni){
        pacienteServicio.eliminar(dni);
        
        return "redirect:/paciente";
    }
    
    @PostMapping("/editar/{dni}")
    public String editarPaciente(@PathVariable("dni")Integer dni,
                                 @RequestParam String nombre,
                                 @RequestParam String apellido,
                                 @RequestParam String obras,
                                 @RequestParam Integer telefono,
                                 @RequestParam String email){
        try {
            PacienteEntidad nPaciente = pacienteServicio.buscarPorDNI(dni);
            if(!nombre.isEmpty()){
                nPaciente.setNombre(nombre);
            }
            if(!apellido.isEmpty()){
                nPaciente.setApellido(apellido);
            }
            if(!obras.isEmpty()){
                ObraSocialEntidad obraSocial = obrasServicio.buscarPorNombre(obras);
                if(obraSocial != null){
                    nPaciente.setObraSocial(obraSocial);
                }else{
                    ObraSocialEntidad sinObraSocial = obrasServicio.buscarPorNombre("Sin Obra Social");
                    nPaciente.setObraSocial(sinObraSocial);
                }
            }
            if(telefono != null){
                nPaciente.setTelefono(telefono);
            }
            if(!email.isEmpty()){
                nPaciente.setEmail(email);
            }
            pacienteServicio.guardarPaciente(nPaciente);
        } catch (Exception e) {
        }
        return "lista-cliente.html";
    }
}

