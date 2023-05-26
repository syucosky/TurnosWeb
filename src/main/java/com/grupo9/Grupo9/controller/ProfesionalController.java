
package com.grupo9.Grupo9.controller;

import com.grupo9.Grupo9.entidades.EspecialidadEntidad;
import com.grupo9.Grupo9.entidades.ObraSocialEntidad;
import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
import com.grupo9.Grupo9.servicios.EspecialidadServicio;
import com.grupo9.Grupo9.servicios.ObraSocialService;
import com.grupo9.Grupo9.servicios.ProfesionalService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/profesional")
public class ProfesionalController {
   
    @Autowired
    ProfesionalService profesionalService;
    @Autowired
    ObraSocialService obrasServicio;
    @Autowired
    EspecialidadServicio especialidadServicio;
    
    @GetMapping("/registro-profesional")
    public String registrarPaciente(ModelMap modelo){
        List<ObraSocialEntidad> obras = new ArrayList();
        obras = obrasServicio.buscarTodas();
        modelo.addAttribute("obras",obras); 
        return "registro-profesional.html";
    }
    
    
    @PostMapping("/registro-profesional")
    public String registrarProfesiona(@RequestParam(value = "dni" )Integer dni,
                                      @RequestParam(value = "nombre")String nombre,
                                      @RequestParam(value = "apellido")String apellido,
                                      @RequestParam(value = "email")String email,
                                      @RequestParam(value = "password")String password,                                    
                                      @RequestParam(value = "sexo")String sexo,
                                      @RequestParam(value = "telefono")String telefono,
                                      @RequestParam(value = "ubicacion")String ubicacion,
                                      @RequestParam(value = "tipoAtencion")String tipoAtencion){
        try {
            ProfesionalEntidad profesional = new ProfesionalEntidad(dni, nombre, email,password,apellido,sexo,ubicacion, tipoAtencion);
            profesionalService.guardarProfesional(profesional);

        } catch (Exception e) {
            
        }
            
        return "perfil-profesional.html";
    }
    
    @GetMapping("/perfil")
    public String perfilProfesional(ModelMap modelo){
        try {      
            List<EspecialidadEntidad> especialidades = new ArrayList();
            especialidades = especialidadServicio.obtenerEspecialidades();
            modelo.addAttribute("especialidades",especialidades);
        } catch (Exception e) {
            System.out.println("error");
        }
        return "perfil-profesional.html";
    }
    
    @PostMapping("/perfil")
    public String seleccionarEspecialidad(@RequestParam(value = "especialidad") String especialidad,
                                          @RequestParam(value = "email") String email){
        ProfesionalEntidad profesional = profesionalService.buscarPorEmail(email);
        EspecialidadEntidad espe = especialidadServicio.buscarPorNombre(especialidad);
        profesional.setEspecialidad(espe);
        profesionalService.guardarProfesional(profesional);
        
        return "redirecto:/profesional/perfil";
    }
    
    
    // ENDPOINT QUE VA A IR PARA EL PERFIL DE ADMIN
    @PostMapping("/crearEspe")
    public String crearEsp(@RequestParam(value = "nombre") String nombre){
        EspecialidadEntidad espe = new EspecialidadEntidad();
        espe.setNombre(nombre);
        especialidadServicio.crearEspecialidad(espe);
        return "perfil-profesional.html";
    }
   
}
