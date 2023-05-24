//
//package com.grupo9.Grupo9.servicios;
//
//import com.grupo9.Grupo9.entidades.TurnosEntidad;
//import com.grupo9.Grupo9.entidades.PacienteEntidad;
//import com.grupo9.Grupo9.entidades.EspecialidadEntidad;
//import com.grupo9.Grupo9.excepciones.MiExcepcion;
//import com.grupo9.Grupo9.repositorios.TurnosRepository;
//import com.grupo9.Grupo9.repositorios.PacienteRepositorio;
//import com.grupo9.Grupo9.repositorios.EspecialidadRepositorio;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.List;
//import java.util.Optional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TurnosService {
//    @Autowired
//    private TurnosRepository turnosRepositorio;
//    
//    @Autowired
//    private EspecialidadRepositorio especialidadRepositorio;
//    
//    @Autowired
//    private PacienteRepositorio pacienteRepositorio;
//    
//    @Transactional
//    public void crearTurnos(LocalDate fecha, LocalTime hora, String medico, String lugar, Integer especialidadId, PacienteEntidad paciente) throws MiExcepcion, Exception{
//        EspecialidadEntidad especialidad = especialidadRepositorio.findById(especialidadId).get();
//        try{
//            validarNombre(medico, "medico");
//            validarNombre(lugar, "lugar");
//            validarFecha(fecha);
//            validarHora(hora);
//            validarPaciente(paciente);
//            
//            TurnosEntidad turnos = new TurnosEntidad();
//            
//            turnos.setFecha(fecha);
//            turnos.setHora(hora);
//            turnos.setMedico(medico);
//            turnos.setLugar(lugar);
//            turnos.setAlta(true);
//            turnos.setPaciente(paciente);
////            turnos.setEspecialidad(especialidad);
//            
//            turnosRepositorio.save(turnos);
//        }catch(MiExcepcion ex){
//            throw ex;
//        }catch(Exception e){
//            throw e;
//        }
//    }
//    
//    @Transactional
//    public void modificar(Integer turnosId, Integer paciente_dni, LocalDate fecha, LocalTime hora, String medico, String lugar, EspecialidadEntidad especialidad) throws MiExcepcion, Exception{
//        try{
//            validarNombre(medico, "medico");
//            validarNombre(lugar, "lugar");
//            validarFecha(fecha);
//            validarHora(hora);
//            validarEspecialidad(especialidad);
//            
//            TurnosEntidad turnos = turnosRepositorio.obtenerTurnosPaciente(paciente_dni, turnosId).
//                    orElseThrow(() -> new MiExcepcion("Error al obtner turno"));
//            
//            turnos.setFecha(fecha);
//            turnos.setHora(hora);
//            turnos.setMedico(medico);
//            turnos.setLugar(lugar);
//            
////            turnos.setEspecialidad(especialidad);
//            turnosRepositorio.save(turnos);
//        }catch(MiExcepcion ex){
//            throw ex;
//        }catch(Exception e){
//            throw e;
//        }        
//    }
//    
//    @Transactional(readOnly = true)
//    public List<TurnosEntidad> buscarTodos() throws Exception{
//        try{
//            return turnosRepositorio.findAll();
//        }catch(Exception e){
//            throw new Exception("Error al obtener turnos");
//        }
//    }
//    
//    @Transactional(readOnly = true)
//    public List<TurnosEntidad> buscarMes(Integer paciente_dni) throws Exception{
//        try{
//            LocalDate fechadeHoy = LocalDate.now();
//            return turnosRepositorio.obtenerTurnosMes(paciente_dni, fechadeHoy);
//        }catch(Exception e){
//            throw new Exception("Error al obtener turnos");
//        }
//    }
//    
//    @Transactional(readOnly = true)
//    public List<TurnosEntidad> buscarFuturos(Integer paciente_dni) throws Exception{
//        try{
//            LocalDate fechadeHoy = LocalDate.now();
//            return turnosRepositorio.obtenerTurnosFuturo(paciente_dni, fechadeHoy);
//        }catch(Exception e){
//            throw new Exception("Error al obtener turnos");
//        }
//    }
//    
//    @Transactional(readOnly = true)
//    public List<TurnosEntidad> buscarPorPaciente(Integer paciente_dni) throws Exception{
//        try{
//            return turnosRepositorio.obtenerTurnosPaciente(paciente_dni);
//        }catch(Exception e){
//            throw new Exception("Error al buscar por DNI");
//        }
//    }
//    
//    @Transactional(readOnly = true)
//    public TurnosEntidad buscarPorDNI(Integer dni) throws Exception{
//        try{
//            Optional<TurnosEntidad> turnosOptional = turnosRepositorio.findById(dni);
//            return turnosOptional.orElse(null);
//        }catch(Exception e){
//            throw new Exception("Error al buscar por DNI");
//        }
//    }
//    
//    @Transactional
//    public void baja(Integer paciente_dni, Integer turnosId) throws MiExcepcion{
//        try{
//            TurnosEntidad turnos = turnosRepositorio.obtenerTurnosPaciente(paciente_dni, turnosId).orElseThrow(()
//                    -> new MiExcepcion("Error al obtener turno"));
//            turnos.setAlta(turnos.getAlta() ? false : true);
//            turnosRepositorio.save(turnos);
//        }catch(MiExcepcion ex){
//            throw ex;
//        }catch(Exception e){
//            throw e;
//        }
//    }
//    
//    @Transactional
//    public void alta(Integer id) throws MiExcepcion{
//        try{
//            Optional<TurnosEntidad> turnosOptional = turnosRepositorio.findById(id);
//            if(turnosOptional.isPresent()){
//                TurnosEntidad turnos = turnosOptional.get();
//                turnos.setAlta(turnos.getAlta() ? true : false);
//                turnosRepositorio.save(turnos);
//            }else{
//                throw new MiExcepcion("No se encontró el Id");
//            }
//        }catch(MiExcepcion ex){
//            throw ex;
//        }catch(Exception e){
//            throw e;
//        }
//    }
//    
//    
//    //Validaciones
//    public void validarNombre(String nombre, String tipo) throws MiExcepcion, Exception{
//        try{
//            if(nombre == null){
//                throw new MiExcepcion("Nombre del " + tipo + " no fue cargado");
//            }else if(nombre.trim().isEmpty()){
//                throw new MiExcepcion("Nombre del " + tipo + " no puede estar vacío");
//            }else if(nombre.length() < 2){
//                throw new MiExcepcion("Nombre del " + tipo + " no puede tener menos de una letra");
//            }else if(tipo.equalsIgnoreCase("medico")){
//                if(nombre.matches(".*\\d.*")){
//                    throw new MiExcepcion("Nombre del " + tipo + " no puede contener números");
//                }
//            }
//        }catch(MiExcepcion ex){
//            throw ex;
//        }catch(Exception e){
//            throw e;
//        }
//    }
//    
//    public void validarFecha(LocalDate fecha) throws MiExcepcion{
//        try{
//            if(fecha == null){
//                throw new MiExcepcion("Ingrese una fecha");
//            }else if(fecha.toString().trim().isEmpty()){
//                throw new MiExcepcion("El campo fecha no puede estar vacio");
//            }
//            
//            LocalDate fechaActual = LocalDate.now();
//            
//            if(fecha.isBefore(fechaActual)){
//                throw new MiExcepcion("La fecha ingresada no es válida");
//            }
//        }catch(MiExcepcion ex){
//            throw ex;
//        }catch(Exception e){
//            throw e;
//        }
//    }
//    
//    public void validarHora(LocalTime hora) throws MiExcepcion{
//        try{
//            if(hora == null){
//                throw new MiExcepcion("Ingrese un horario");
//            }else if(hora.toString().trim().isEmpty()){
//                throw new MiExcepcion("El campo hora no puede estar vácio");
//            }
//        }catch(MiExcepcion ex){
//            throw ex;
//        }catch(Exception e){
//            throw e;
//        }
//    }
//    
//    public void validarPaciente(PacienteEntidad paciente) throws MiExcepcion, Exception{
//        try{
//            if(paciente == null){
//                throw new MiExcepcion("Error al obtener perfil");
//            }
//        }catch(MiExcepcion ex){
//            throw ex;
//        }catch(Exception e){
//            throw e;
//        }
//    }
//    
//    public void validarEspecialidad(EspecialidadEntidad especialidad) throws MiExcepcion{
//        try{
//            if(especialidad == null){
//                throw new MiExcepcion("Error al obtener especialidad");
//            }
//        }catch(MiExcepcion ex){
//            throw ex;
//        }catch(Exception e){
//            throw e;
//        }
//    }
//}
