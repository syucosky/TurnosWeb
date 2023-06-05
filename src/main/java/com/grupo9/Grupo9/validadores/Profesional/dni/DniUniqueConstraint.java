/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.grupo9.Grupo9.validadores.Profesional.dni;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = DniUniqueValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )  
@Retention(RetentionPolicy.RUNTIME) 
public @interface DniUniqueConstraint {
       String message() default "Dni ya existe";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
