package com.grupo9.Grupo9.controller;

import com.grupo9.Grupo9.entidades.ObraSocialEntidad;
import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
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

    @GetMapping("/registro-profesionales")
    public String registrarProfesional(ModelMap modelo) {
        List<ObraSocialEntidad> obras = new ArrayList();
        obras = obrasServicio.buscarTodas();
        modelo.addAttribute("obras", obras);
        modelo.addAttribute("modo", "registrar");

        return "registro-profesional.html";
    }

    @PostMapping("/registro-profesional")
    public String registrarProfesional(@RequestParam(value = "dni") Integer dni,
            @RequestParam() String nombre,
            @RequestParam() String apellido,
            @RequestParam() String email,
            @RequestParam() String password,
            @RequestParam() String sexo,
            @RequestParam() String telefono,
            @RequestParam() String ubicacion,
            @RequestParam() String tipoAtencion,
            @RequestParam() Long obraSocialId) {

        ProfesionalEntidad profesional = new ProfesionalEntidad(dni, nombre, email, password, apellido, sexo, ubicacion, tipoAtencion, telefono
        );

        profesionalService.guardarProfesional(profesional, obraSocialId);

        return "redirect:/";
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
}
