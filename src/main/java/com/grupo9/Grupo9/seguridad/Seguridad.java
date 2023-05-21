//
//package com.grupo9.Grupo9.seguridad;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class Seguridad extends WebSecurityConfigurerAdapter{
//   
//    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        
//        http.authorizeRequests()
//                .antMatchers("/img/seleccion-usuario/*","/img/registros/*","/css/*","/js/*","/img/*","/img/Login/*","/**")
//                .permitAll()
//                .and().csrf().disable();
//                
//        
//    }
//}
