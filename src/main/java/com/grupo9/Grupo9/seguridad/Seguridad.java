
package com.grupo9.Grupo9.seguridad;

import com.grupo9.Grupo9.servicios.PacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Seguridad extends WebSecurityConfigurerAdapter{
   
    @Autowired
   PacienteServicio pacienteServicio;
   
   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception{
       auth.userDetailsService(pacienteServicio).passwordEncoder(new BCryptPasswordEncoder());
   }
   
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        
        http.authorizeRequests()
                .antMatchers("/img/*","/css/*","/js/*","/**")
                .permitAll()
                .and().formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginCheck")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/inicio").permitAll()
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login").permitAll()
                .and().csrf().disable();
                
        
    }
}
