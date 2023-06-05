package com.grupo9.Grupo9.controller;

import com.grupo9.Grupo9.entidades.EspecialidadEntidad;
import com.grupo9.Grupo9.entidades.Filtro;
import com.grupo9.Grupo9.entidades.ImagenEntidad;
import com.grupo9.Grupo9.entidades.ObraSocialEntidad;
import com.grupo9.Grupo9.entidades.PacienteEntidad;
import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
import com.grupo9.Grupo9.entidades.TurnosEntidad;
import com.grupo9.Grupo9.servicios.EspecialidadServicio;
import com.grupo9.Grupo9.servicios.ImagenServicio;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    ImagenServicio imagenServicio;

    @GetMapping("/registro-profesionales")
    public String registrarProfesional(ModelMap modelo) {
        List<ObraSocialEntidad> obras = new ArrayList();
        List<EspecialidadEntidad> especialidades = new ArrayList();
        obras = obrasServicio.buscarTodas();
        especialidades = especialidadServicio.obtenerEspecialidades();
        modelo.addAttribute("obras", obras);
        modelo.addAttribute("modo", "registrar");
        modelo.addAttribute("especialidades", especialidades);
        return "registro-profesional.html";
    }

    @PostMapping("/registro-profesional")
    public String registrarProfesiona(ModelMap modelo, @RequestParam(value = "dni") Integer dni,
            @RequestParam(value = "nombre") String nombre,
            @RequestParam(value = "apellido") String apellido,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "sexo") String sexo,
            @RequestParam(value = "telefono") String telefono,
            @RequestParam(value = "ubicacion") String ubicacion,
            @RequestParam(value = "tipoAtencion") String tipoAtencion,
            @RequestParam() Long obraSocialId,
            @RequestParam() MultipartFile imagen,
            @RequestParam() Integer especialidadId) {
        try {

            ProfesionalEntidad profesional = new ProfesionalEntidad(dni, nombre, email, password, apellido, sexo, ubicacion, tipoAtencion, telefono);

            profesional.setImagen(imagenServicio.guardar(imagen));

            profesional.setEspecialidad(especialidadServicio.buscarPorId(especialidadId));
           
//            if (profesionalService.buscarPorDni(dni) != null) {
//                throw new Exception("DNI ya existe");
//            }
            profesionalService.guardarProfesional(profesional, true, obraSocialId);

        } catch (Exception e) {
            modelo.addAttribute("error", e.getMessage());
            return "error";
        }

        return "redirect:/profesional/perfil";
    }
  @GetMapping("/filtrar/especialidad")
     public String inicio(@ModelAttribute("filtro")Filtro filtro, ModelMap modelo){
	List<ProfesionalEntidad> listaFiltrada;
        listaFiltrada = profesionalService.buscarProfesionalesEspecialidad(filtro.getEspecialidad());
	modelo.addAttribute("profesionales", listaFiltrada);
	modelo.addAttribute("filtro", filtro);
	return "inicio.html";
     }
     @GetMapping("/filtrar/obraSocial")
     public String filtarPorObraSocial(@ModelAttribute("filtro")Filtro filtro, ModelMap modelo){
	List<ProfesionalEntidad> listaFiltrada;
        listaFiltrada = profesionalService.buscarProfesionalesPorObraSocial(filtro.getObraSocial());
	modelo.addAttribute("profesionales", listaFiltrada);
	modelo.addAttribute("filtro", filtro);
	return "inicio.html";
     }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','PROFESIONALNOAPTO')")
    @GetMapping("/perfil")
    public String perfilProfesional(ModelMap modelo) {
        try {
            List<EspecialidadEntidad> especialidades = new ArrayList();
            especialidades = especialidadServicio.obtenerEspecialidades();
            modelo.addAttribute("especialidades", especialidades);
            List<TurnosEntidad> turnos = turnosService.obtenerTurnos();
            modelo.addAttribute("turnos", turnos);
        } catch (Exception e) {
            System.out.println("error");
        }
        return "perfil-profesional.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','PROFESIONALNOAPTO')")
    @PostMapping("/perfil")
    public String seleccionarEspecialidad(
            @RequestParam(value = "especialidad") String especialidad,
            @RequestParam(value = "email") String email) {
        ProfesionalEntidad profesional = profesionalService.buscarPorEmail(email);
        EspecialidadEntidad espe = especialidadServicio.buscarPorNombre(especialidad);
        profesional.setEspecialidad(espe);
        profesionalService.guardarProfesional(profesional, false, null);

        return "redirect:/profesional/perfil";
    }

    @PostMapping("/editar")
    public String editarProfesional(
            @RequestParam(value = "dni") Integer dni,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "telefono") String telefono,
            @RequestParam(value = "ubicacion") String ubicacion,
            @RequestParam(value = "tipoAtencion") String tipoAtencion,
            @RequestParam(value = "obraSocialId") Long obraSocialId,
            @RequestParam() Integer especialidadId,
            @RequestParam() MultipartFile imagen) throws Exception {

        ProfesionalEntidad profesional = profesionalService.buscarPorDni(dni);
        profesional.setImagen(imagenServicio.guardar(imagen));
        profesional.setEmail(email);
        profesional.setUbicacion(ubicacion);
        profesional.setTelefono(telefono);
        profesional.setTipoAtencion(tipoAtencion);
        profesional.setEspecialidad(especialidadServicio.buscarPorId(especialidadId));

        profesionalService.guardarProfesional(profesional, false, obraSocialId);

        return "redirect:/";
    }

    @GetMapping("/editar")
    public String editarProfesional(ModelMap modelo, @RequestParam() Integer dni) {

        ProfesionalEntidad profesional = profesionalService.buscarPorDni(dni);
        List<EspecialidadEntidad> especialidades = especialidadServicio.obtenerEspecialidades();

        modelo.addAttribute("modo", "editar");
        modelo.addAttribute("datosProfesional", profesional);
        modelo.addAttribute("especialidades", especialidades);
        List<ObraSocialEntidad> obras = new ArrayList();
        obras = obrasServicio.buscarTodas();
        modelo.addAttribute("obras", obras);
        return "registro-profesional.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','PROFESIONALNOAPTO')")
    // ENDPOINT QUE VA A IR PARA EL PERFIL DE ADMIN
    @PostMapping("/crearEspe")
    public String crearEsp(@RequestParam(value = "nombre") String nombre) {
        EspecialidadEntidad espe = new EspecialidadEntidad();
        espe.setNombre(nombre);
        especialidadServicio.crearEspecialidad(espe);
        return "perfil-profesional.html";
    }

//    @DeleteMapping("/eliminar")
//    public String eliminarProfesional(ModelMap modelo, @RequestParam() Integer dni) {
//        profesionalService.eliminarProfesional(dni);
//        return "redirect:/";
//    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','PROFESIONALNOAPTO')")
    @PostMapping("/turnosSeleccionados")
    public String guardarTurnos(@RequestParam(value = "turnosSelec") List<String> turnosSelec,
            @RequestParam(value = "email") String email) {
        ProfesionalEntidad profesional = profesionalService.buscarPorEmail(email);
        for (String t : turnosSelec) {
            TurnosEntidad turno = turnosService.findById(Integer.parseInt(t));
            profesional.getTurnos().add(turno);
        }
        profesionalService.guardarProfesional(profesional, false, null);
        return "redirect:/profesional/perfil";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','PROFESIONALNOAPTO')")
    @GetMapping("/turnosReservados")
    public String mostrarTurnosAgendados(ModelMap modelo, @RequestParam(value = "email") String email) {
        ProfesionalEntidad profesional = profesionalService.buscarPorEmail(email);
        PacienteEntidad paciente = profesionalService.buscarPaciente(profesional.getDni());
        TurnosEntidad turno = turnosService.findById(paciente.getTurnoId());
        modelo.addAttribute("turno", turno);
        modelo.addAttribute("paciente", paciente);
        return "profesionalTurnosReservados.html";
    }
}
