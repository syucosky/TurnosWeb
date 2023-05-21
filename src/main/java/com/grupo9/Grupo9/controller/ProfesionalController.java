
package com.grupo9.Grupo9.controller;

import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
import com.grupo9.Grupo9.servicios.ProfesionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/profesional")
public class ProfesionalController {
   
    @Autowired
    ProfesionalService profesionalService;
    
    @GetMapping("/registro-profesional")
    public String registrarProfesional(){
        
        return "/registro-profesional";
    }
    
    
    @PostMapping("/registro-profesional")
    public String registrarProfesiona(@RequestParam(value = "dni" )Integer dni,
                                      @RequestParam(value = "nombre")String nombre,
                                      @RequestParam(value = "apellido")String apellido,
                                      @RequestParam(value = "email")String email,
                                      @RequestParam(value = "password")String password){
        try {
            ProfesionalEntidad profesional = new ProfesionalEntidad(dni, nombre, email, password, apellido);     
            profesionalService.guardarProfesional(profesional);
        } catch (Exception e) {
            
        }
            
        return "redirect:/";
    }
    
   
}
