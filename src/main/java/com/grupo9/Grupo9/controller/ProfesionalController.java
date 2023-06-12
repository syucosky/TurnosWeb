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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/imagen/{id}")
    public void obtenerImagen(@PathVariable Integer id, HttpServletResponse response) throws IOException {

        response.setContentType("image/jpeg"); // Or whatever format you wanna use

        ImagenEntidad imagen = imagenServicio.buscarPorId(id);

        InputStream is = new ByteArrayInputStream(imagen.getContenido());
        IOUtils.copy(is, response.getOutputStream());
    }

    @GetMapping("/registro-profesionales")
    public String registrarProfesional(
            @ModelAttribute("profesional") ProfesionalEntidad profesional,
            ModelMap modelo) {
        cargarModelo(modelo);
        modelo.addAttribute("modo", "registrar");
        return "registro-profesional.html";
    }

    @PostMapping("/registro-profesional")
    public String registrarProfesiona(ModelMap modelo,
            @Valid @ModelAttribute("profesional") ProfesionalEntidad profesional,
            BindingResult result,
            @RequestParam() Long[] obrasSocialesId,
            @RequestParam() MultipartFile imagen_file,
            @RequestParam() Integer especialidadId) {
        try {

            if (result.hasErrors()) {
                cargarModelo(modelo);
                modelo.addAttribute("modo", "registrar");
                profesional.setEspecialidad(especialidadServicio.buscarPorId(especialidadId));
                for (Long obraSocialeId : obrasSocialesId) {
                    profesional.getObraSocial().add(obrasServicio.buscarPorId(obraSocialeId));
                }
                return "registro-profesional.html";
            }
            ProfesionalEntidad newProfesional = new ProfesionalEntidad(
                    profesional.getDni(),
                    profesional.getNombre(),
                    profesional.getEmail(),
                    profesional.getPassword(),
                    profesional.getApellido(),
                    profesional.getSexo(),
                    profesional.getUbicacion(),
                    profesional.getTipoAtencion(),
                    profesional.getTelefono());

            newProfesional.setImagen(imagenServicio.guardar(imagen_file));

            newProfesional.setEspecialidad(especialidadServicio.buscarPorId(especialidadId));

            profesionalService.guardarProfesional(newProfesional, true, obrasSocialesId);

        } catch (Exception e) {
            modelo.addAttribute("error", e.getMessage());
            return "error";
        }

        return "redirect:/profesional/perfil?dni="+profesional.getDni();
    }

    private void cargarModelo(ModelMap modelo) {
        List<ObraSocialEntidad> obras = obrasServicio.buscarTodas();
        List<EspecialidadEntidad> especialidades = especialidadServicio.obtenerEspecialidades();
        modelo.addAttribute("obras", obras);
        modelo.addAttribute("especialidades", especialidades);
    }

    @GetMapping("/filtrar")
    public String inicio(@ModelAttribute("filtro") Filtro filtro, ModelMap modelo) {
        List<ProfesionalEntidad> listaFiltrada;
        listaFiltrada = profesionalService.buscarProfesionalesEspecialidad(filtro.getEspecialidad());
        modelo.addAttribute("listaEspecialistas", listaFiltrada);
        modelo.addAttribute("filtro", filtro);
        return "inicio.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','PROFESIONALNOAPTO')")
    @GetMapping("/perfil")
    public String perfilProfesional(ModelMap modelo, @RequestParam Integer dni) {
        try {
            ProfesionalEntidad profesional = profesionalService.buscarPorDni(dni);
            List<EspecialidadEntidad> especialidades = new ArrayList();
            especialidades = especialidadServicio.obtenerEspecialidades();
            modelo.addAttribute("especialidades", especialidades);
            List<TurnosEntidad> turnos = turnosService.obtenerTurnos();
            modelo.addAttribute("turnos", turnos);
            modelo.addAttribute("profesional", profesional);
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
            ModelMap modelo,
            @Valid @ModelAttribute("profesional") ProfesionalEntidad profesional,
            BindingResult result,
            @RequestParam() Long[] obrasSocialesId,
            @RequestParam() Integer especialidadId,
            @RequestParam() MultipartFile imagen_file) throws Exception {

        if (result.hasErrors()) {

            modelo.addAttribute("modo", "editar");
            profesional.setEspecialidad(especialidadServicio.buscarPorId(especialidadId));
            for (Long obraSocialeId : obrasSocialesId) {
                profesional.getObraSocial().add(obrasServicio.buscarPorId(obraSocialeId));
            }

            modelo.addAttribute("profesional", profesional);
            cargarModelo(modelo);
            return "registro-profesional";
        }
        ProfesionalEntidad profesionalEdit = profesionalService.buscarPorDni(profesional.getDni());
        profesionalEdit.setImagen(imagenServicio.guardar(imagen_file));
        profesionalEdit.setEmail(profesional.getEmail());
        profesionalEdit.setUbicacion(profesional.getUbicacion());
        profesionalEdit.setTelefono(profesional.getTelefono());
        profesionalEdit.setTipoAtencion(profesional.getTipoAtencion());
        profesionalEdit.setEspecialidad(especialidadServicio.buscarPorId(especialidadId));

        profesionalService.guardarProfesional(profesionalEdit, false, obrasSocialesId);

        return "redirect:/";
    }

    @GetMapping("/editar")
    public String editarProfesional(
            @ModelAttribute("profesional") ProfesionalEntidad profesional,
            ModelMap modelo, @RequestParam() Integer dni) {

        profesional = profesionalService.buscarPorDni(dni);
        modelo.addAttribute("modo", "editar");
        modelo.addAttribute("profesional", profesional);
        cargarModelo(modelo);
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
