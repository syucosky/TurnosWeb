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
    private EspecialidadRepositorio especialidadRepositorio;
    
    private String mensaje = "No existe ninguna especialidad asociada con el ID %s";
    
    @Transactional
    public void crearEspecialidad(String nombre, Integer idProfesional) throws Exception, MiExcepcion{
        try{
            validacionNombre(nombre, "Nombre");
            
            EspecialidadEntidad especialidad = new EspecialidadEntidad();
            
            especialidad.setNombre(nombre);
            especialidad.setIdProfesional(idProfesional);
//            especialidad.setAlta(true);
            
            especialidadRepositorio.save(especialidad);
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }
    
    @Transactional
    public void modificarEspecialidad(EspecialidadEntidad especialidad) throws Exception, MiExcepcion{
        try{
            validacionNombre(especialidad.getNombre(), "Nombre");
            
            EspecialidadEntidad esp = especialidadRepositorio.findById(especialidad.getId()).orElseThrow(()
                    -> new MiExcepcion(String.format(mensaje, especialidad.getId())));
            esp.setNombre(esp.getNombre());
            especialidadRepositorio.save(esp);
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }
    
    @Transactional(readOnly = true)
    public List<EspecialidadEntidad> obtenerEspecialidades() throws Exception{
        try{
            return especialidadRepositorio.findAll();
        }catch(Exception e){
            throw e;
        }
    }
    
    @Transactional(readOnly = true)
    public List<EspecialidadEntidad> buscarPorProfesional(Integer id) throws Exception{
        try{
            return especialidadRepositorio.buscarPorProfesional(id);
        }catch(Exception e){
            throw e;
        }
    }
    
    @Transactional(readOnly = true)
    public EspecialidadEntidad obtenerEspecialidadIdPaciente(Integer especialidadId, Integer profesionalId) throws Exception{
        try{
            Optional<EspecialidadEntidad> especialidadOptional = especialidadRepositorio.buscarPorId(especialidadId);
            return especialidadOptional.orElse(null);
        }catch(Exception e){
            throw e;
        }
    }
    
    @Transactional(readOnly = true)
    public EspecialidadEntidad obtenerEspecialidadId(Integer id) throws Exception{
        try{
            Optional<EspecialidadEntidad> especialidadOptional = especialidadRepositorio.findById(id);
            return especialidadOptional.orElse(null);
        }catch(Exception e){
            throw e;
        }
    }
    
    @Transactional
    public void eliminarEspecialidad(Integer id){
        especialidadRepositorio.deleteById(id);
    }
    
    @Transactional
    public void baja(Integer id){
        especialidadRepositorio.baja(id, false);
    }
    
    @Transactional
    public void alta(Integer id){
        especialidadRepositorio.baja(id, true);
    }
    
    //Validaciones
    public void validacionNombre(String nombre, String tipo) throws Exception, MiExcepcion{
        try{
            if(nombre == null){
                throw new MiExcepcion(tipo + " no fue cargado");
            }else if(nombre.trim().isEmpty()){
                throw new MiExcepcion(tipo + " invalido, no puede estar vacio");
            }
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }
}
