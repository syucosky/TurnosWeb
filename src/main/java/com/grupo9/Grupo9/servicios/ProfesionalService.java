package com.grupo9.Grupo9.servicios;

import com.grupo9.Grupo9.entidades.EspecialidadEntidad;
import com.grupo9.Grupo9.entidades.PacienteEntidad;
import com.grupo9.Grupo9.entidades.ImagenEntidad;
import com.grupo9.Grupo9.entidades.ObraSocialEntidad;
import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
import com.grupo9.Grupo9.entidades.TurnosEntidad;
import com.grupo9.Grupo9.enumeraciones.Rol;
import com.grupo9.Grupo9.repositorios.ImagenRepository;
import com.grupo9.Grupo9.excepciones.MiExcepcion;
import com.grupo9.Grupo9.repositorios.ProfesionalRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfesionalService implements UserDetailsService {

    @Autowired
    ProfesionalRepository profesionalRepositorio;

    @Autowired
    ObraSocialService obrasServicio;
    @Autowired
    ImagenRepository imagenRepository;

    @Transactional
    public void guardarProfesional(ProfesionalEntidad profesional, Boolean ok, Long[] obraSocialesId) {
        try {

            if (obraSocialesId != null) {
                
                // ELIMINO TODAS LAS OBRAS SOCIALES DEL PROFESIONAL
                // PARA LUEGO AGREGAR O ELIMINAR LAS DE UI
                profesional.getObraSocial().clear();
                for (Long obraSocialId : obraSocialesId) {
                    ObraSocialEntidad oSocial = obrasServicio.buscarPorId(obraSocialId);                                    
                    profesional.getObraSocial().add(oSocial);                  
                }
            }

            if (ok) {
                String passCod = profesional.getPassword();
                profesional.setPassword(new BCryptPasswordEncoder().encode(passCod));
                profesionalRepositorio.save(profesional);
            } else {
                profesionalRepositorio.save(profesional);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public PacienteEntidad buscarPaciente(Integer dni) {
        return profesionalRepositorio.buscarPaciente(dni);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ProfesionalEntidad profesional = profesionalRepositorio.findByEmail(email);
        if (profesional != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + profesional.getRol().toString());
            permisos.add(p);
            User user = new User(profesional.getEmail(), profesional.getPassword(), permisos);
            return user;
        } else {
            return null;
        }
    }

    public ProfesionalEntidad buscarPorEmail(String email) {
        return profesionalRepositorio.findByEmail(email);
    }

    public void dardeAlta(Integer dni) {
        profesionalRepositorio.altaProfesional(dni, Rol.PROFESIONALAPTO);
    }

    public void dardeBaja(Integer dni) {
        profesionalRepositorio.altaProfesional(dni, Rol.PROFESIONALNOAPTO);
    }

    public List<ProfesionalEntidad> listarProfesionales() {

        return profesionalRepositorio.findAll();
    }

    public void setEspecialidad(Integer espeId, Integer dni) {
        profesionalRepositorio.setEspecialidad(espeId, dni);
    }

    public ProfesionalEntidad obtenerProfesionalPorId(Integer dni) {
        return profesionalRepositorio.findById(dni).get();
    }


    /*Validación de profesional*/
    public void validacionNombre(String nombre, String tipo) throws Exception, MiExcepcion {
        try {
            if (nombre == null) {
                throw new MiExcepcion(tipo + " no fue cargado");
            } else if (nombre.trim().isEmpty()) {
                throw new MiExcepcion(tipo + " invalido, no puede estar en blanco");
            } else if (nombre.length() < 1) {
                throw new MiExcepcion(tipo + " invalido, debe tener más de una letra");
            } else if (nombre.matches(".*\\d.*")) {
                throw new MiExcepcion(tipo + " invalido, no puede contener números");
            }
        } catch (MiExcepcion ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        }
    }

    public void validacionDNI(Integer dni) throws Exception, MiExcepcion {
        try {
            if (dni == null) {
                throw new MiExcepcion("DNI no fue cargado");
            } else if (dni < 0) {
                throw new MiExcepcion("DNI invalido, no puede ser un número negativo");
            } else if (Long.toString(dni).matches("^[0-9][^a-zA-Z]{6,9}$") == false) {
                throw new MiExcepcion("DNI invalido");
            } else if (profesionalRepositorio.findById(dni) != null) {
                throw new MiExcepcion("DNI ingresado se encuentra asociado a una cuenta");
            }
        } catch (MiExcepcion ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        }
    }

    public void validarEmail(String email) throws MiExcepcion {
        if (email == null || email.trim().isEmpty()) {
            throw new MiExcepcion("El Email no puede estar vacio.");
        }

        if (profesionalRepositorio.findByEmail(email) != null) {
            throw new MiExcepcion("Ya existe un usuario asociado al correo ingresado");
        }
        if (!(email.contains("@") && email.contains(".com"))) {
            throw new MiExcepcion("Debe ingresar un formato de correo valido.");
        }
    }

    public void validacionProfesional(ProfesionalEntidad profesional) throws Exception, MiExcepcion {
        try {
            if (profesional == null) {
                throw new MiExcepcion("Los datos del paciente no fueron cargados");
            }
        } catch (MiExcepcion ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        }
    }

    public void validarObraSocial(ProfesionalEntidad profesional) throws Exception, MiExcepcion {

    }

    //Metodos CRUD
    @Transactional
    public void editarNombre(String nombre, ProfesionalEntidad profesional) throws Exception, MiExcepcion {
        try {
            validacionNombre(nombre, "Nombre");
            profesional.setNombre(nombre);
            profesionalRepositorio.save(profesional);
        } catch (MiExcepcion ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void editarApellido(String apellido, ProfesionalEntidad profesional) throws Exception, MiExcepcion {
        try {
            validacionNombre(apellido, "Apellido");
            profesional.setApellido(apellido);
            profesionalRepositorio.save(profesional);
        } catch (MiExcepcion ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void editarDNI(Integer dni, ProfesionalEntidad profesional) throws Exception, MiExcepcion {
        try {
            validacionDNI(dni);
            profesional.setDni(dni);
            profesionalRepositorio.save(profesional);
        } catch (MiExcepcion ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void editarEmail(String email, ProfesionalEntidad profesional) throws Exception {
        try {
            validarEmail(email);
            profesional.setEmail(email);

            profesionalRepositorio.save(profesional);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void editarProfesional(String nombre, String apellido, Integer dni, ObraSocialEntidad obraSocial, ProfesionalEntidad profesional) throws Exception, MiExcepcion {

        try {
            validacionNombre(nombre, "Nombre");
            validacionNombre(apellido, "Apellido");
            validacionDNI(dni);
            validarObraSocial(profesional);            //validacionFechaNacimiento(fechaNacimiento);

            profesional.setNombre(nombre);
            profesional.setApellido(apellido);
            profesional.setDni(dni);

            //historiacliniaServicio.modificarEdad(Period.between(cliente.getFechaNacimiento(), LocalDate.now()).getYears(), cliente);
            profesionalRepositorio.save(profesional);
        } catch (MiExcepcion ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        }
    }

    /*DELETE*/
    public void eliminarProfesional(Integer dni) {
        profesionalRepositorio.deleteById(dni);
    }

    public ProfesionalEntidad buscarPorDni(Integer dni) {
        return profesionalRepositorio.findById(dni).orElse(null);

    }

}
