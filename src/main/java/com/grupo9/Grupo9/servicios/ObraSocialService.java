
package com.grupo9.Grupo9.servicios;

import com.grupo9.Grupo9.entidades.ObraSocialEntidad;
import com.grupo9.Grupo9.repositorios.ObraSocialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ObraSocialService {
    
    @Autowired
    ObraSocialRepositorio obraSocialRepositorio;
    
    @Transactional(readOnly = true)
    public ObraSocialEntidad buscarPorNombre(String nombre){     
        return obraSocialRepositorio.findByName(nombre);
    }
}
