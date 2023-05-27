package com.grupo9.Grupo9.servicios;

import com.grupo9.Grupo9.entidades.PacienteEntidad;
import com.grupo9.Grupo9.entidades.HistorialClinicoEntidad;
import com.grupo9.Grupo9.excepciones.MiExcepcion;
import com.grupo9.Grupo9.repositorios.HistorialClinicoRepositorio;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HistorialClinicoServicio {
    @Autowired
    private HistorialClinicoRepositorio historialClinicoRepositorio;
    
    //Metodos CRUD
    @Transactional
    public void guardarHistorialClinico(String grupoSanguineo, Double peso, Integer altura,
            String enfermedades, LocalDate ultimoChequeo, PacienteEntidad paciente) throws Exception, MiExcepcion{
        try{
            validarGrupoSanguineo(grupoSanguineo);
            validarPeso(peso);
            validarAltura(altura);
            validarEnfermedades(enfermedades);
            
            validarPaciente(paciente);
            
            HistorialClinicoEntidad historialClinico = new HistorialClinicoEntidad();
            
//            Integer edad = Period.between(paciente.getFechaNacimiento(), LocalDate.now()).getYears();
            
            historialClinico.setGrupoSanguineo(grupoSanguineo);
            historialClinico.setPeso(peso);
            historialClinico.setAltura(altura);
            historialClinico.setEnfermedades(enfermedades);
            historialClinico.setUltimoChequeo(ultimoChequeo);
            historialClinico.setPaciente(paciente);
//            historialClinico.setEdad(edad);
            
            historialClinicoRepositorio.save(historialClinico);
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }  
    
    @Transactional
    public void modificarHistorialClinico(String grupoSanguineo, Double peso, Integer altura,
            String enfermedades, LocalDate ultimoChequeo, PacienteEntidad paciente) throws Exception, MiExcepcion{
        try{
            validarGrupoSanguineo(grupoSanguineo);
            validarPeso(peso);
            validarAltura(altura);
            validarEnfermedades(enfermedades);
            validarUltimoChequeo(ultimoChequeo);
            validarPaciente(paciente);
            
            HistorialClinicoEntidad historial = obtenerHistorialClinicoId(paciente.getDni());
            
            historial.setGrupoSanguineo(grupoSanguineo);
            historial.setPeso(peso);
            historial.setAltura(altura);
            historial.setEnfermedades(enfermedades);
            historial.setUltimoChequeo(ultimoChequeo);
            
            historialClinicoRepositorio.save(historial);
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }
    
    @Transactional
    public void modificarEdad(Integer edad, PacienteEntidad paciente) throws Exception, MiExcepcion{
        try{
            validarPaciente(paciente);
            HistorialClinicoEntidad historial = obtenerHistorialClinicoId(paciente.getDni());
            
            if(historial != null){
                historial.setEdad(edad);
                historialClinicoRepositorio.save(historial);
            }
        }catch(MiExcepcion ex){
        
        }catch(Exception e){
            throw e;
        }
    }
    
    @Transactional
    public void modificarPeso(Double peso, PacienteEntidad paciente) throws Exception, MiExcepcion{
        try{
            validarPeso(peso);
            validarPaciente(paciente);
            HistorialClinicoEntidad historial = obtenerHistorialClinicoId(paciente.getDni());
            historial.setPeso(peso);
            historialClinicoRepositorio.save(historial);
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }
    
    @Transactional
    public void modificarAltura(Integer altura, PacienteEntidad paciente) throws Exception, MiExcepcion{
        try{
            validarAltura(altura);
            validarPaciente(paciente);
            HistorialClinicoEntidad historial = obtenerHistorialClinicoId(paciente.getDni());
            historial.setAltura(altura);
            historialClinicoRepositorio.save(historial);
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }
    
    @Transactional
    public void modificarGrupoSanguineo(String grupoSanguineo, PacienteEntidad paciente) throws Exception, MiExcepcion{
        try{
            validarGrupoSanguineo(grupoSanguineo);
            validarPaciente(paciente);
            HistorialClinicoEntidad historial = obtenerHistorialClinicoId(paciente.getDni());
            historial.setGrupoSanguineo(grupoSanguineo);
            historialClinicoRepositorio.save(historial);
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }
    
    @Transactional
    public void modificarUltimoChequeo(LocalDate ultimoChequeo, PacienteEntidad paciente) throws Exception, MiExcepcion{
        try{
            validarUltimoChequeo(ultimoChequeo);
            validarPaciente(paciente);
            HistorialClinicoEntidad historial = obtenerHistorialClinicoId(paciente.getDni());
            historial.setUltimoChequeo(ultimoChequeo);
            historialClinicoRepositorio.save(historial);
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }
    
    //Metodos de validación
    public void validarGrupoSanguineo(String grupoSanguineo) throws Exception, MiExcepcion{
        try{
            if(grupoSanguineo == null){
                throw new MiExcepcion("Grupo sanguineo no fue cargado");
            }else if(grupoSanguineo.trim().isEmpty()){
                throw new MiExcepcion("Grupo sanguineo invalido, no puede estar en blanco");
            }else if(grupoSanguineo.length() < 1){
                throw new MiExcepcion("Grupo sanguineo invalido, debe tener más de una letra");
            }
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }
    
    public void validarPeso(Double peso) throws Exception, MiExcepcion{
        try{
            if(peso == null){
                throw new MiExcepcion("Peso no fue cargado");
            }else if(peso <= 0){
                throw new MiExcepcion("Peso invalido");
            }
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }
    
    public void validarAltura(Integer altura) throws Exception, MiExcepcion{
        try{
            if(altura == null){
                throw new MiExcepcion("Altura no fue cargada");
            }else if(altura <= 0){
                throw new MiExcepcion("Altura invalida");
            }
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }
    
    public void validarEnfermedades(String enfermadad) throws Exception, MiExcepcion{
        try{
            if(enfermadad == null){
                throw new MiExcepcion("Enfermedad no fue cargada");
            }else if(enfermadad.trim().isEmpty()){
                throw new MiExcepcion("Enfermedad invalida, no puede estar en blanco");
            }else if(enfermadad.length() < 1){
                throw new MiExcepcion("Enfermedad invalida, debe tener más de una letra");
            }
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }
    
    public void validarUltimoChequeo(LocalDate ultimoChequeo) throws Exception, MiExcepcion{
        try{
            LocalDate actual = LocalDate.now();
            if(ultimoChequeo == null){
                throw new MiExcepcion("La fecha del ultimo chuequeo no fue cargada");
            }else if(ultimoChequeo.isAfter(actual)){
                throw new MiExcepcion("Ultimo chequeo no puede ser una fecha superior a la actual");
            }
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }
    
    public void validarPaciente(PacienteEntidad paciente) throws Exception, MiExcepcion{
        try{
            if(paciente == null){
                throw new MiExcepcion("Los datos del paciente no fueron cargados");
            }
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }
    
    //Metodos de consultas
    @Transactional(readOnly = true)
    public List<HistorialClinicoEntidad> obtenerHistorialClinico() throws Exception{
        try{
            return historialClinicoRepositorio.findAll();
        }catch(Exception e){
            throw e;
        }
    }
    
    @Transactional(readOnly = true)
    public HistorialClinicoEntidad obtenerHistorialClinicoId(Integer idPaciente) throws Exception, MiExcepcion{
        try{
            HistorialClinicoEntidad historialClinico = historialClinicoRepositorio.obtenerHistorialClinico(idPaciente).orElseThrow(() -> 
                    new MiExcepcion("Error al obtener historial clinico"));
            return historialClinico;
        }catch(Exception e){
            throw e;
        }
    }
    
    @Transactional(readOnly = true)
    public HistorialClinicoEntidad obtenerHistorialClinicoIdPaciente(Integer idPaciente) throws Exception, MiExcepcion{
        try{
            HistorialClinicoEntidad historialClinico = historialClinicoRepositorio.obtenerHistorialClinico(idPaciente).orElse(null);
            return historialClinico;
        }catch(Exception e){
            throw e;
        }
    }
}
