package com.grupo9.Grupo9.controller;

import com.grupo9.Grupo9.entidades.HistorialClinicoEntidad;
import com.grupo9.Grupo9.entidades.PacienteEntidad;
import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
import com.grupo9.Grupo9.repositorios.HistorialClinicoRepositorio;
import com.grupo9.Grupo9.servicios.HistorialClinicoServicio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
@Controller
@RequestMapping("/historiaClinica")
public class HistorialClinicaController {


    @Autowired
    HistorialClinicoServicio hisCliServicio;
   
    
    
 

    @GetMapping(("/listar"))
    public String listarHistoriaClinica(Model model) {
        List<HistorialClinicoEntidad> historial = hisCliServicio.obtenerHistorial();
        model.addAttribute("historiaClinica", historial);
        return "ver-historiaClinica.html";
    }

    @GetMapping("/nuevo")
    public String registrarHistoria(Model model){
    return "historiaClinica-Crear.html";
    }

   @PostMapping("/guardarHistCli")
    public String registrarHistoria(
                                      @RequestParam(value = "edad")Integer edad,
                                      @RequestParam(value = "nombre")String nombre,
                                      @RequestParam(value = "grupoSanguinio")String grupoSanguinio,
                                      @RequestParam(value = "peso")Double peso,                                    
                                      @RequestParam(value = "altura")Integer altura,
                                      @RequestParam(value = "enfermedades")String enfermedades,
                                      @RequestParam(value = "ultimoChequeo") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate ultimoChequeo){
        try {
            HistorialClinicoEntidad hisCli;
            hisCli = new HistorialClinicoEntidad(edad, nombre, grupoSanguinio, peso, altura, enfermedades, ultimoChequeo);
        hisCliServicio.guardarHistCli(hisCli ,true);

        } catch (Exception e) {
            
        }
            
        return "redirect:/historiaClinica/listar";
    }
  @GetMapping("/eliminar/{id}")
    public String eliminarHistoriaClinica(@PathVariable Integer id) {
        hisCliServicio.eliminarHistoria(id);
        return "redirect:/historiaClinica/listar";
    }
    
    @GetMapping("/editar/{id}")
    public String obtenerHistCliPorId(ModelMap model, @PathVariable Integer id) {
        HistorialClinicoEntidad hisCliEditar = hisCliServicio.obtenerHistCliPorId(id);
        model.addAttribute("hisCliEditar", hisCliEditar);
        return "editar-historiaClinica.html";
    }

    @PostMapping("/guardarHistEdicion")
    public String guardarHistEdicion(
            @RequestParam(value = "id")Integer id,
            @RequestParam(value = "nombre")String nombre,
            @RequestParam(value = "edad")Integer edad,
            @RequestParam(value = "grupoSanguinio")String grupoSanguinio,
            @RequestParam(value = "peso")Double peso,
            @RequestParam(value = "altura")Integer altura,
            @RequestParam(value = "enfermedades")String enfermedades,
            @RequestParam(value = "ultimoChequeo") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate ultimoChequeo){
        try {
            HistorialClinicoEntidad hisClien = new HistorialClinicoEntidad(edad, nombre, grupoSanguinio, peso, altura, enfermedades, ultimoChequeo);
            hisClien.setId(id);
            hisCliServicio.guardarHistCli(hisClien ,true);

        } catch (Exception e) {

        }

        return "redirect:/historiaClinica/listar";
    }
    
    
    
}



  
    
