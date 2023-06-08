/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo9.Grupo9.servicios;

import com.grupo9.Grupo9.entidades.ImagenEntidad;
import com.grupo9.Grupo9.repositorios.ImagenRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepository imagenRepository;
    //Valido y guardo imagen

    public ImagenEntidad guardar(MultipartFile imagen) throws IOException, Exception {

        if (imagen
                == null) {
            throw new Exception("Imagen requerida");
        }

        ImagenEntidad img = new ImagenEntidad();

        img.setNombre(imagen.getName());
        img.setMime(imagen.getContentType());
        img.setContenido(imagen.getBytes());

        ImagenEntidad imagenGuardada = imagenRepository.save(img);
        return imagenGuardada;
    }

    public ImagenEntidad buscarPorId(Integer Id) {
        return imagenRepository.findById(Id).orElse(null);
    }

}
