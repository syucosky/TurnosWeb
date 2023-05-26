
package com.grupo9.Grupo9.servicios;

import com.grupo9.Grupo9.entidades.ObraSocialEntidad;
import com.grupo9.Grupo9.repositorios.ObraSocialRepositorio;
import java.util.List;
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
    @Transactional(readOnly = true)
    public List<ObraSocialEntidad> buscarTodas(){
        List<ObraSocialEntidad> obras = obraSocialRepositorio.findAll();
        return obras;
    }
    
    public ObraSocialEntidad buscarPorId(Long id){
    return obraSocialRepositorio.findById(id).get();
    }
}
