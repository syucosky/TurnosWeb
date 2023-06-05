/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo9.Grupo9.validadores.Profesional.dni;

import com.grupo9.Grupo9.servicios.ProfesionalService;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Administrator
 */
public class DniUniqueValidator implements
        ConstraintValidator<DniUniqueConstraint, Integer> {

    @Autowired
    ProfesionalService profesionalService;

    @Override
    public void initialize(DniUniqueConstraint contactNumber) {
    }

    @Override
    public boolean isValid(Integer dniField,
            ConstraintValidatorContext cxt) {

        if (profesionalService.buscarPorDni(dniField) != null) {
            return false;
        }
        return true;
    }

}
