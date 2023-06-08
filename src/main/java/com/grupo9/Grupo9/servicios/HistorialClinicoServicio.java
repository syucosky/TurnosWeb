package com.grupo9.Grupo9.servicios;

import com.grupo9.Grupo9.entidades.HistorialClinicoEntidad;
import com.grupo9.Grupo9.entidades.ObraSocialEntidad;
import com.grupo9.Grupo9.entidades.PacienteEntidad;
import com.grupo9.Grupo9.entidades.ProfesionalEntidad;
import com.grupo9.Grupo9.repositorios.HistorialClinicoRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HistorialClinicoServicio {
    
    @Autowired
   HistorialClinicoRepositorio hisCliRepository;
    
   public List<HistorialClinicoEntidad> obtenerHistorial(){
       return hisCliRepository.findAll();
       
        
}

   public void guardarHistCli(HistorialClinicoEntidad hisCli, Boolean ok)    {

                hisCliRepository.save(hisCli);
           
     
    }
          
   

   

    public void eliminarHistoria(Integer id) {
        hisCliRepository.deleteById(id); 
     
    }
   public HistorialClinicoEntidad obtenerHistCliPorId(Integer id) {
        Optional<HistorialClinicoEntidad> resultado = hisCliRepository.findById(id);
        return resultado.orElse(null);
    }
 
 
}
