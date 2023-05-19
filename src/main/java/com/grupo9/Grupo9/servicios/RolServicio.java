package com.grupo9.Grupo9.servicios;

import com.grupo9.Grupo9.entidades.RolEntidad;
import com.grupo9.Grupo9.repositorios.RolRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolServicio {
    
    @Autowired
    private RolRepositorio rolRepositorio;
    
    @Transactional(readOnly = true)
    public List<RolEntidad> buscarTodos(){
        try{
            return rolRepositorio.findAll();
        }catch(Exception e){
            throw e;
        }
    }
}
