package com.grupo9.Grupo9.controller;

import com.grupo9.Grupo9.entidades.EspecialidadEntidad;
import com.grupo9.Grupo9.entidades.ImagenEntidad;
import com.grupo9.Grupo9.entidades.ObraSocialEntidad;
import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
import com.grupo9.Grupo9.entidades.TurnosEntidad;
import com.grupo9.Grupo9.servicios.EspecialidadServicio;
import com.grupo9.Grupo9.servicios.ObraSocialService;
import com.grupo9.Grupo9.servicios.ProfesionalService;
import com.grupo9.Grupo9.servicios.TurnosService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/registro-profesionales")
    public String registrarProfesional(ModelMap modelo) {
        List<ObraSocialEntidad> obras = new ArrayList();
        obras = obrasServicio.buscarTodas();
        modelo.addAttribute("obras", obras);
        modelo.addAttribute("modo", "registrar");

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
                                      @RequestParam(value = "tipoAtencion")String tipoAtencion,
                                      
            @RequestParam() Long obraSocialId,
                                      @RequestParam()MultipartFile imagen){
        try {
            
            ImagenEntidad img = new ImagenEntidad();
            img.setNombre(imagen.getName());
            img.setMime(imagen.getContentType());
            img.setContenido(imagen.getBytes());         
            
            ProfesionalEntidad profesional = new ProfesionalEntidad(dni, nombre, email,password,apellido,sexo,ubicacion, tipoAtencion);
            profesionalService.guardarProfesional(profesional,obraSocialId, img);

        ProfesionalEntidad profesional = new ProfesionalEntidad(dni, nombre, email, password, apellido, sexo, ubicacion, tipoAtencion, telefono
        );

        profesionalService.guardarProfesional(profesional, obraSocialId);

        return "perfil-profesional.html";
    }

    @GetMapping("/perfil")
    public String perfilProfesional(ModelMap modelo) {
        try {
            List<EspecialidadEntidad> especialidades = new ArrayList();
            especialidades = especialidadServicio.obtenerEspecialidades();
            modelo.addAttribute("especialidades", especialidades);
            List<TurnosEntidad> turnos = turnosService.obtenerTurnos();
            modelo.addAttribute("turnos",turnos);
        } catch (Exception e) {
            System.out.println("error");
        }
        return "perfil-profesional.html";
    }

    @PostMapping("/perfil")
    public String seleccionarEspecialidad(
            @RequestParam(value = "especialidad") String especialidad,
            @RequestParam(value = "email") String email) {
        ProfesionalEntidad profesional = profesionalService.buscarPorEmail(email);
        EspecialidadEntidad espe = especialidadServicio.buscarPorNombre(especialidad);
        profesional.setEspecialidad(espe);
        profesionalService.guardarProfesional(profesional, null);

        return "redirecto:/profesional/perfil";
    }

    @PostMapping("/editar")
    public String editarProfesional(@RequestParam(value = "dni") Integer dni,
            @RequestParam(value = "nombre") String nombre,
            @RequestParam(value = "apellido") String apellido,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "sexo") String sexo,
            @RequestParam(value = "telefono") String telefono,
            @RequestParam(value = "ubicacion") String ubicacion,
            @RequestParam(value = "tipoAtencion") String tipoAtencion,
            @RequestParam(value = "obraSocialId") Long obraSocialId) {

        ProfesionalEntidad profesional = profesionalService.buscarPorDni(dni);
        profesional.setEmail(email);
        profesional.setUbicacion(ubicacion);
        profesional.setTelefono(telefono);
        profesional.setTipoAtencion(tipoAtencion);

        profesionalService.guardarProfesional(profesional, obraSocialId);

        return "redirect:/";
    }

    @GetMapping("/editar")
    public String editarProfesional(ModelMap modelo, @RequestParam() Integer dni) {

        ProfesionalEntidad profesional = profesionalService.buscarPorDni(dni);
        modelo.addAttribute("modo", "editar");
        modelo.addAttribute("datosProfesional", profesional);

        List<ObraSocialEntidad> obras = new ArrayList();
        obras = obrasServicio.buscarTodas();
        modelo.addAttribute("obras", obras);
        return "registro-profesional.html";
    }

    // ENDPOINT QUE VA A IR PARA EL PERFIL DE ADMIN
    @PostMapping("/crearEspe")
    public String crearEsp(@RequestParam(value = "nombre") String nombre) {
        EspecialidadEntidad espe = new EspecialidadEntidad();
        espe.setNombre(nombre);
        especialidadServicio.crearEspecialidad(espe);
        return "perfil-profesional.html";
    }

    @DeleteMapping("/eliminar")
    public String eliminarProfesional(ModelMap modelo, @RequestParam() Integer dni) {
        profesionalService.eliminarProfesional(dni);
        return "redirect:/";
    }
}
