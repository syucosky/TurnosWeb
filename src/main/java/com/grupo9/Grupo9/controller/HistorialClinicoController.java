package com.grupo9.Grupo9.controller;

import com.grupo9.Grupo9.entidades.PacienteEntidad;
import com.grupo9.Grupo9.entidades.HistorialClinicoEntidad;
import com.grupo9.Grupo9.excepciones.MiExcepcion;
import com.grupo9.Grupo9.servicios.PacienteServicio;
import com.grupo9.Grupo9.servicios.HistorialClinicoServicio;
import java.time.LocalDate;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/historial-clinico")
public class HistorialClinicoController {
    
    @Autowired
    private HistorialClinicoServicio historialClinicoServicio;
    
    @Autowired
    private PacienteServicio pacienteServicio;
    
    //metodos GET
    @GetMapping
    public ModelAndView mostrarHistorialClinico(HttpSession session, HttpServletRequest request) throws MiExcepcion, Exception{
        ModelAndView mav = new ModelAndView("");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if(flashMap != null){
            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }
        
        PacienteEntidad paciente = pacienteServicio.obtenerPerfil((Integer) session.getAttribute("dni"));
        HistorialClinicoEntidad historial = historialClinicoServicio.obtenerHistorialClinicoId(paciente.getDni());
        
        mav.addObject("perfil", paciente);
        mav.addObject("historial", historial);
        
        return mav;
    }
    
    //metodos POST
    @PostMapping("/guardar-historial")
    public RedirectView guardarhistorial(@RequestParam String grupoSanguineo, @RequestParam Double peso,
            @RequestParam Integer altura, @RequestParam String enfermedades,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate ultimoChequeo,
            HttpSession sesion, RedirectAttributes attributes) throws Exception{
        try{
            PacienteEntidad paciente = pacienteServicio.obtenerPerfil((Integer) sesion.getAttribute("dni"));
            
            historialClinicoServicio.guardarHistorialClinico(grupoSanguineo, peso, altura, enfermedades, ultimoChequeo, paciente);
            
            attributes.addFlashAttribute("exito-name", "Historial clinico registrado exitosamente");
        }catch(Exception e){
            attributes.addFlashAttribute("error-name", e.getMessage());
        } 
        return new RedirectView("/perfil");
    }
    
    @PostMapping("/modificar-peso")
    public RedirectView modificarPeso(@RequestParam Double peso, HttpSession sesion,
            RedirectAttributes attributes) throws Exception{
        try{
            PacienteEntidad paciente = pacienteServicio.obtenerPerfil((Integer) sesion.getAttribute("dni"));
            
            historialClinicoServicio.modificarPeso(peso, paciente);
            
            attributes.addFlashAttribute("exito-name", "El peso se modifico exitosamente");
        }catch(Exception e){
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/perfil");
    }
    
    @PostMapping("/modificar-altura")
    public RedirectView modificarAltura(@RequestParam Integer altura, HttpSession sesion,
            RedirectAttributes attributes) throws Exception{
        try{
            PacienteEntidad paciente = pacienteServicio.obtenerPerfil((Integer) sesion.getAttribute("dni"));
            
            historialClinicoServicio.modificarAltura(altura, paciente);
            
            attributes.addFlashAttribute("exito-name", "La altura se modifico exitosamente");
        }catch(Exception e){
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/perfil");
    }
    
    @PostMapping("/modificar-sanguineo")
    public RedirectView modificarGrupoSanguineo(@RequestParam String sanguineo, HttpSession sesion,
            RedirectAttributes attributes) throws Exception{
        try{
            PacienteEntidad paciente = pacienteServicio.obtenerPerfil((Integer) sesion.getAttribute("dni"));
            
            historialClinicoServicio.modificarGrupoSanguineo(sanguineo, paciente);
            
            attributes.addFlashAttribute("exito-name", "El grupo sanguineo se modifico exitosamente");
        }catch(Exception e){
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/perfil");
    }
    
    @PostMapping("/modificar-historial")
    public RedirectView modificarHistorial(@RequestParam String grupoSanguineo, @RequestParam Double peso,
            @RequestParam Integer altura, @RequestParam String enfermedades, LocalDate ultimoChequeo,
            HttpSession sesion, RedirectAttributes attributes) throws Exception{
        try{
            PacienteEntidad paciente = pacienteServicio.obtenerPerfil((Integer) sesion.getAttribute("dni"));
            
            historialClinicoServicio.modificarHistorialClinico(grupoSanguineo, peso, altura, enfermedades, ultimoChequeo, paciente);
            
            attributes.addFlashAttribute("exito-name", "Historial clinico registrado exitosamente");
        }catch(Exception e){
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/perfil");
    }
    
    @PostMapping("/editar-ultimoChequeo")
    public RedirectView editarUltimoChequeo(RedirectAttributes attributes, HttpServletRequest request,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate ultimoChequeo, HttpSession sesion) throws Exception{
        try{
            PacienteEntidad paciente = pacienteServicio.obtenerPerfil((Integer) sesion.getAttribute("dni"));
            
            historialClinicoServicio.modificarUltimoChequeo(ultimoChequeo, paciente);
            
            attributes.addFlashAttribute("exito-name", "La fecha de la ultima consulta se modifico exitosamente");
        }catch(Exception e){
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/perfil");
    }
}
