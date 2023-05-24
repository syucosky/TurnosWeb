
package com.grupo9.Grupo9.servicios;

import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
import com.grupo9.Grupo9.repositorios.ProfesionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfesionalService {
    @Autowired
    ProfesionalRepository profesionalRepositorio;
    
    public void guardarProfesional(ProfesionalEntidad profesional){
        try {
            profesionalRepositorio.save(profesional);
        } catch (Exception e) {
            throw e; 
        }
    }
}
