package com.grupo9.Grupo9.controller;

import com.grupo9.Grupo9.entidades.ObraSocialEntidad;
import com.grupo9.Grupo9.entidades.PacienteEntidad;
import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
import com.grupo9.Grupo9.entidades.TurnosEntidad;
import com.grupo9.Grupo9.servicios.ObraSocialService;
import com.grupo9.Grupo9.servicios.PacienteServicio;
import com.grupo9.Grupo9.servicios.ProfesionalService;
import com.grupo9.Grupo9.servicios.TurnosService;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/paciente")
public class PacienteController {
    
    @Autowired  
    PacienteServicio pacienteServicio;
    @Autowired
    ObraSocialService obrasServicio;
    @Autowired
    ProfesionalService profesionalService;
    @Autowired
    TurnosService turnosService;

    
    
    @GetMapping("/registro-paciente")
    public String registrarPaciente(ModelMap modelo) {
      List<ObraSocialEntidad> obras = new ArrayList();
      obras = obrasServicio.buscarTodas();
      modelo.addAttribute("obras", obras);
      return "registro-paciente.html";
    }
    
    //ESTO TRAE TODOS LOS PACIENTES PARA EL HTML PACIENTES.HTML HAY QUE VERIFICAR PORQUE ME PARECE QUE VA EN LA ENTIDAD ADMI PORQUE SERIA ESA FUNCIONALIDAD, PERO TODAVIA NO ESTA IMPLEMENTADA
      @GetMapping("/pacientes")
      public ModelAndView obtenerPacientes() throws Exception {
        List<Map<String, String>> pacientes = pacienteServicio.obtenerPacientesADMI();
        ModelAndView modelAndView = new ModelAndView("lista_pacientes");
        modelAndView.addObject("pacientes", pacientes);
        return modelAndView;
      }
    //ELIMINAR UN PACIENTE POR DNI EN EL HTML ELIMINARPACIENTE QUE SERIA PARA EL ADMI 
      @GetMapping("/EliminarPaciente/{dni}")
  public String eliminarPaciente(@PathVariable Integer dni) {
    pacienteServicio.eliminar(dni);
    return "redirect:/pacientes";
  }
    
    @PostMapping("/editar")
    public String editarPaciente(@PathVariable("dni")Integer dni,
                                 @RequestParam String nombre,
                                 @RequestParam String apellido,
                                 @RequestParam String obras,
                                 @RequestParam String telefono,
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
            pacienteServicio.guardarPaciente(nPaciente,false);
        } catch (Exception e) {
        }
        return "lista-cliente.html";
    }
    
    
    @GetMapping("/editar")
    public String editarPaciente (ModelMap modelo, @RequestParam() Integer dni) {

        PacienteEntidad paciente = pacienteServicio.buscarPorDNI(dni);
        modelo.addAttribute("modo", "editar");
        
        modelo.addAttribute("datosPaciente", paciente);

       
        return "registro-paciente.html";
    }
    
    
    @PostMapping("/registro-paciente")
    public String registrarPaciente(
                                    @RequestParam(value = "dni") Integer dni,
                                    @RequestParam(value = "nombre") String nombre,
                                    @RequestParam(value = "apellido") String apellido,
                                    @RequestParam(value = "email") String email,
                                    @RequestParam(value = "obraselec") String obraselec,
                                    @RequestParam(value = "fechaNac") String fNacimiento,
                                    @RequestParam(value = "telefono") String telefono,
                                    @RequestParam(value = "sexo") String sexo,
                                    @RequestParam(value = "password") String password){
        try {
            ObraSocialEntidad obraSocial = obrasServicio.buscarPorNombre(obraselec);
            PacienteEntidad paciente = new PacienteEntidad(dni, nombre, apellido, fNacimiento, sexo, email, obraSocial, telefono, password);
            pacienteServicio.guardarPaciente(paciente,true);
        } catch (Exception e) {
        }   
        return "redirect:/";
    }
    
    @GetMapping("/turnos")
    public String reservarTurno(ModelMap modelo,ModelMap modeloDos,
                                @RequestParam(value = "profe") String profe){
        ProfesionalEntidad profesional = profesionalService.buscarPorEmail(profe);
        List<TurnosEntidad> turnos = turnosService.turnosIdProf(profesional.getDni());
        List<Integer> pacienteTurno = pacienteServicio.turnosPorIdProf(profesional.getDni());
        Iterator<TurnosEntidad> iterator = turnos.iterator();
        while (iterator.hasNext()) {
            TurnosEntidad objeto = iterator.next();
            if (pacienteTurno.contains(objeto.getId())) {
                iterator.remove();
            }
        }
            modelo.addAttribute("turnos",turnos);
            modeloDos.addAttribute("profesional",profesional);
        
        return "turnos.html";  
    }
    // PARA RESERVAR TURNO TENGO QUE GUARDAR EN PACIENTE ID DE TURNO E ID PROFESIONAL
    @PostMapping("/turnos/reservar")
    public String reservarTurno(@RequestParam(value = "idTurno")Integer idTurno,
                                @RequestParam(value = "idProf") Integer idProf,
                                @RequestParam(value = "email")String email){
        try {
            PacienteEntidad paciente = pacienteServicio.buscarPorEmail(email);
            paciente.setTurnoId(idTurno);
            paciente.setProfesionalId(idProf);
            pacienteServicio.guardarPaciente(paciente,false);
        } catch (Exception e) {
        }
        
        return "redirect:/inicio";
    }
//    @GetMapping("/turno/cancelar")
//    public String cancelarTurno(@RequestParam(value = "email")String email){
//        
//    }
            
}

