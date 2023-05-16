package com.grupo9.Grupo9.controller;

import com.grupo9.Grupo9.entidades.PacienteEntidad;
import com.grupo9.Grupo9.servicios.PacienteServicio;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registrar")
public class PacienteController {
    
    @Autowired
    PacienteServicio pacienteServicio;
    
    
    @PostMapping("/paciente")
    public String registrarPaciente(
                                    @RequestParam("dni") Integer dni,
                                    @RequestParam("nombre") String nombre,
                                    @RequestParam("apellido") String apellido,
                                    @RequestParam("email") String email,
                                    @RequestParam(value = "obras", required = false) String obras,
                                    @RequestParam("fNacimiento") Date fNacimiento,
                                    @RequestParam("telefono") Integer telefono,
                                    @RequestParam("sexo") String sexo,
                                    @RequestParam("password") String password){
        try {
            pacienteServicio.guardarPaciente(dni, nombre, apellido,
                fNacimiento, sexo, email, obras,telefono, password);
        } catch (Exception e) {
        }   
        return "index.html";
    }
}
