
package com.grupo9.Grupo9.controller;

import com.grupo9.Grupo9.entidades.ObraSocialEntidad;
import com.grupo9.Grupo9.entidades.PacienteEntidad;
import com.grupo9.Grupo9.servicios.ObraSocialService;
import com.grupo9.Grupo9.servicios.PacienteServicio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    PacienteServicio pacienteServicio;
    @Autowired
    ObraSocialService obraSocialServicio;
    
    
    @GetMapping("/")
    public String vista(){    
        return "login.html";
    }
    @GetMapping("/seleccion-usuario")
    public String seleccionUsuario(){    
        return "seleccion-usuario.html";
    }


    @GetMapping("/Modificar/{id}")
    public String modificar(@PathVariable("dni") Integer dni){
        
        return "modificar.html";
    }


    
}
