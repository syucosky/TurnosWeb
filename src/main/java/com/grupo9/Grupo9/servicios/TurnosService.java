
package com.grupo9.Grupo9.servicios;

import com.grupo9.Grupo9.entidades.TurnosEntidad;
import com.grupo9.Grupo9.repositorios.TurnosRepository;
import com.grupo9.Grupo9.repositorios.PacienteRepositorio;
import com.grupo9.Grupo9.repositorios.EspecialidadRepositorio;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class TurnosService {
   @Autowired
   TurnosRepository turnosRepository;
   
   public List<TurnosEntidad> obtenerTurnos(){
       return turnosRepository.findAll();
   }
   public TurnosEntidad findById(Integer id){
       
       return turnosRepository.findById(id).get();
   }
   public List<TurnosEntidad> turnosIdProf(Integer id){
       return turnosRepository.turnosIdProf(id);
   }
}
