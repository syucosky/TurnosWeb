package com.grupo9.Grupo9.servicios;

import com.grupo9.Grupo9.entidades.ObraSocialEntidad;
import com.grupo9.Grupo9.entidades.PacienteEntidad;
import com.grupo9.Grupo9.repositorios.PacienteRepositorio;
import com.grupo9.Grupo9.excepciones.MiExcepcion;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.grupo9.Grupo9.repositorios.ObraSocialRepositorio;
import java.util.ArrayList;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class PacienteServicio{
    
    @Autowired
    PacienteRepositorio pacienteRepositorio;

//    @Autowired
//    private HistorialClinicoServicio historialclinicoServicio;
    
    //Metodos CRUD
    
    public List<Integer> turnosPorIdProf(Integer profId){
        
        return pacienteRepositorio.turnosPorIdProf(profId);
    }
    public PacienteEntidad buscarPorEmail(String email){
        return pacienteRepositorio.findByEmail(email);
    }
    @Transactional
    public void guardarPaciente(PacienteEntidad paciente,Boolean ok) throws Exception, MiExcepcion{
        
        try{
            if(ok){
                String passCod = paciente.getPassword();
                paciente.setPassword(new BCryptPasswordEncoder().encode(passCod));
                pacienteRepositorio.save(paciente);
            }else{
                pacienteRepositorio.save(paciente);
            }
        }catch(Exception e){
            throw e;
        }     
    }
    
    
     @Transactional(readOnly = true)
     public List<PacienteEntidad> todosLosPacientes(){
         return pacienteRepositorio.findAll();
     }
     @Transactional
    public void eliminar(Integer dni) { 
        try {
            pacienteRepositorio.deleteById(dni);
        } catch (Exception e) {
            throw e;
        }
    }
    @Transactional
    public void editarNombre(String nombre, PacienteEntidad paciente) throws Exception, MiExcepcion{
        try{
            validacionNombre(nombre, "Nombre");
            paciente.setNombre(nombre);
            pacienteRepositorio.save(paciente);
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }
    
    @Transactional
    public void editarApellido(String apellido, PacienteEntidad paciente) throws Exception, MiExcepcion{
        try{
            validacionNombre(apellido, "Apellido");
            paciente.setApellido(apellido);
            pacienteRepositorio.save(paciente);
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }
    
    @Transactional
    public void editarDNI(Integer dni, PacienteEntidad paciente) throws Exception, MiExcepcion{
        try{
            validacionDNI(dni);
            paciente.setDni(dni);
            pacienteRepositorio.save(paciente);
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }
    
    @Transactional
    public void editarFechaNacimiento(String fechaNacimiento, PacienteEntidad paciente) throws Exception, MiExcepcion {
        try {
            validacionFechaNacimiento(fechaNacimiento);
            paciente.setFechaNacimiento(fechaNacimiento);
            //hisotoriaclinicaServicio.modificarEdad(Period.between(cliente.getFechaNacimiento(), LocalDate.now()).getYears(), cliente);
            pacienteRepositorio.save(paciente);
//        } catch (MiExcepcion ex) {
//            throw ex;
        } catch (Exception e) {
            throw e;
        }
    }
    
    @Transactional
    public void editarEmail(Integer dni, String email) throws Exception {
        try {
            PacienteEntidad paciente = buscarPorDNI(dni);

            if (!paciente.getEmail().equals(email)) {
                validarEmail(email);
                paciente.setEmail(email);
            }
            pacienteRepositorio.save(paciente);
        } catch (Exception e) {
            throw e;
        }
    }
    
    @Transactional
    public void editarPaciente(String nombre, String apellido, Integer dni, String fechaNacimiento, PacienteEntidad paciente) throws Exception, MiExcepcion {

        try {
            validacionNombre(nombre, "Nombre");
            validacionNombre(apellido, "Apellido");
            validacionFechaNacimiento(fechaNacimiento);

            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setDni(dni);
            paciente.setFechaNacimiento(fechaNacimiento);
     
            //historiacliniaServicio.modificarEdad(Period.between(cliente.getFechaNacimiento(), LocalDate.now()).getYears(), cliente);

            pacienteRepositorio.save(paciente);
        } catch (MiExcepcion ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        }
    }
    
    //Metodos de validacion
    public void validacionNombre(String nombre, String tipo) throws Exception, MiExcepcion{
        try{
            if(nombre == null){
                throw new MiExcepcion(tipo + " no fue cargado");
            }else if(nombre.trim().isEmpty()){
                throw new MiExcepcion(tipo + " invalido, no puede estar en blanco");
            }else if(nombre.length() < 1){
                throw new MiExcepcion(tipo + " invalido, debe tener más de una letra");
            }else if(nombre.matches(".*\\d.*")){
                throw new MiExcepcion(tipo + " invalido, no puede contener números");
            }
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }
    
    public void validacionDNI(Integer dni) throws Exception, MiExcepcion{
        try{
            if(dni == null){
                throw new MiExcepcion("DNI no fue cargado");
            }else if(dni < 0){
                throw new MiExcepcion("DNI invalido, no puede ser un número negativo");
            }else if(Long.toString(dni).matches("^[0-9][^a-zA-Z]{6,9}$") == false){
                throw new MiExcepcion("DNI invalido");
            }else if(pacienteRepositorio.findById(dni) != null){
                throw new MiExcepcion("DNI ingresado se encuentra asociado a una cuenta");
            }
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e;
        }
    }
    
    public void validacionFechaNacimiento(String fechaNacimiento) throws Exception, MiExcepcion{
        try{
            if(fechaNacimiento == null){
                throw new MiExcepcion("La fecha de nacimiento no fue cargada");
            }
        }catch(MiExcepcion ex){
            throw ex;
        }catch(Exception e){
            throw e; 
        }    
    }
    
    public void validarEmail(String email) throws MiExcepcion {
        if (email == null || email.trim().isEmpty()) {
            throw new MiExcepcion("El Email no puede estar vacio.");
        }

        if (pacienteRepositorio.findByEmail(email) != null) {
            throw new MiExcepcion("Ya existe un usuario asociado al correo ingresado");
        }
        if (!(email.contains("@") && email.contains(".com"))) {
            throw new MiExcepcion("Debe ingresar un formato de correo valido.");
        }
    }
    
    public void validacionPaciente(PacienteEntidad paciente) throws Exception, MiExcepcion{
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
    
    //Metodos de consulta
    @Transactional(readOnly = true)
    public List<PacienteEntidad> obtenerPacientes() throws Exception {
      try {
        return pacienteRepositorio.findAll();
      } catch (Exception e) {
        throw e;
      }
    }
    
     @Transactional(readOnly = true)
public List<Map<String, String>> obtenerPacientesADMI() throws Exception {
    try {
        List<PacienteEntidad> pacientesEntidad = pacienteRepositorio.findAll();
        List<Map<String, String>> pacientes = new ArrayList<>();

        for (PacienteEntidad pacienteEntidad : pacientesEntidad) {
            Map<String, String> paciente = new HashMap<>();
            paciente.put("dni", pacienteEntidad.getDni().toString());
            paciente.put("nombre", pacienteEntidad.getNombre());
            paciente.put("apellido", pacienteEntidad.getApellido());
            pacientes.add(paciente);
        }

        return pacientes;
    } catch (Exception e) {
        throw e;
    }
}

    
    @Transactional(readOnly = true)
    public PacienteEntidad obtenerPerfil(Integer dni) throws Exception, MiExcepcion{
        try {
            PacienteEntidad paciente = pacienteRepositorio.findById(dni).orElseThrow(() -> 
                    new MiExcepcion("Error al obtener datos del perfil"));
            return paciente;
        } catch (Exception e) {
            throw e;
        }
    }
    
    @Transactional(readOnly = true)
    public PacienteEntidad buscarPorDNI(Integer dni) {
        try {
            Optional<PacienteEntidad> uOptional = pacienteRepositorio.findById(dni);
            return uOptional.orElse(null);
        } catch (Exception e) {
            throw e;
        }
    }

 

}


