package com.grupo9.Grupo9.servicios;

import com.grupo9.Grupo9.entidades.EspecialidadEntidad;
import com.grupo9.Grupo9.excepciones.MiExcepcion;
import com.grupo9.Grupo9.repositorios.EspecialidadRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EspecialidadServicio {
    @Autowired
    EspecialidadRepositorio especialidadRepositorio;

    public void crearEspecialidad(EspecialidadEntidad especialidad){
        especialidadRepositorio.save(especialidad);
    }
    @Transactional(readOnly = true)
    public List<EspecialidadEntidad> obtenerEspecialidades(){

        return especialidadRepositorio.findAll();
    }
    public EspecialidadEntidad buscarPorNombre(String nombre){
        
        return especialidadRepositorio.findByName(nombre);
    }
    

}
