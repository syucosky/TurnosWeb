
package com.grupo9.Grupo9.controller;

import com.grupo9.Grupo9.entidades.EspecialidadEntidad;
import com.grupo9.Grupo9.entidades.Filtro;
import com.grupo9.Grupo9.entidades.ObraSocialEntidad;
import com.grupo9.Grupo9.entidades.PacienteEntidad;
import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
import com.grupo9.Grupo9.entidades.TurnosEntidad;
import com.grupo9.Grupo9.servicios.EspecialidadServicio;
import com.grupo9.Grupo9.servicios.ObraSocialService;
import com.grupo9.Grupo9.servicios.ProfesionalService;
import com.grupo9.Grupo9.servicios.TurnosService;
import com.grupo9.Grupo9.servicios.UsuarioServicio;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    TurnosService turnosService;
    
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
            profesionalService.guardarProfesional(profesional,true);

        } catch (Exception e) {
            
        }
            
        return "redirect:/profesional/perfil";
    }
    @GetMapping("/buscar-profesionales")
     public String inicio(@ModelAttribute("filtro")Filtro filtro, ModelMap modelo){
	List<ProfesionalEntidad> listaFiltrada;
        listaFiltrada = profesionalService.buscarProfesionales(filtro.getEspecialidad(), filtro.getObraSocial());
	modelo.addAttribute("listaEspecialistas", listaFiltrada);
	modelo.addAttribute("filtro", filtro);
	return "inicio.html";
     }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','PROFESIONALNOAPTO')")
    @GetMapping("/perfil")
    public String perfilProfesional(ModelMap modelo){
        try {      
            List<EspecialidadEntidad> especialidades = new ArrayList();
            especialidades = especialidadServicio.obtenerEspecialidades();
            modelo.addAttribute("especialidades",especialidades);
            List<TurnosEntidad> turnos = turnosService.obtenerTurnos();
            modelo.addAttribute("turnos",turnos);
        } catch (Exception e) {
            System.out.println("error");
        }
        return "perfil-profesional.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','PROFESIONALNOAPTO')")
    @PostMapping("/perfil")
    public String seleccionarEspecialidad(@RequestParam(value = "especialidad") String especialidad,
                                          @RequestParam(value = "email") String email){
        ProfesionalEntidad profesional = profesionalService.buscarPorEmail(email);
        EspecialidadEntidad espe = especialidadServicio.buscarPorNombre(especialidad);
        profesional.setEspecialidad(espe);
        profesionalService.guardarProfesional(profesional,false);
        
        return "redirect:/profesional/perfil";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','PROFESIONALNOAPTO')")
    // ENDPOINT QUE VA A IR PARA EL PERFIL DE ADMIN
    @PostMapping("/crearEspe")
    public String crearEsp(@RequestParam(value = "nombre") String nombre){
        EspecialidadEntidad espe = new EspecialidadEntidad();
        espe.setNombre(nombre);
        especialidadServicio.crearEspecialidad(espe);
        return "perfil-profesional.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','PROFESIONALNOAPTO')")
    @PostMapping("/turnosSeleccionados")
    public String guardarTurnos(@RequestParam(value = "turnosSelec") List<String> turnosSelec,
                                @RequestParam(value = "email") String email){
        ProfesionalEntidad profesional = profesionalService.buscarPorEmail(email);
        for (String t : turnosSelec) {
            TurnosEntidad turno = turnosService.findById(Integer.parseInt(t));
            profesional.getTurnos().add(turno);
        }
        profesionalService.guardarProfesional(profesional,false);        
        return "redirect:/profesional/perfil";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','PROFESIONALNOAPTO')")
    @GetMapping("/turnosReservados")
    public String mostrarTurnosAgendados(ModelMap modelo,@RequestParam(value = "email")String email){
        ProfesionalEntidad profesional = profesionalService.buscarPorEmail(email);
        PacienteEntidad paciente = profesionalService.buscarPaciente(profesional.getDni());
        TurnosEntidad turno = turnosService.findById(paciente.getTurnoId());
        modelo.addAttribute("turno",turno);
        modelo.addAttribute("paciente",paciente);
        return "profesionalTurnosReservados.html";
    }
}
