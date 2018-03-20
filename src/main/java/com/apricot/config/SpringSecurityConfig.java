/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apricot.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Argos
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private ApricotAccessDeniedHandler accessDeniedHandler;
    
    private final String usersQuery = "SELECT users.email, users.password, users.status FROM users WHERE users.email=?";
    private final String rolesQuery = "SELECT users.email, roles.`name` FROM users INNER JOIN user_roles ON user_roles.id_user = users.id_user INNER JOIN roles ON user_roles.id_role = roles.id_role WHERE users.email=?";
   
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
               auth.
                    jdbcAuthentication()
                            .usersByUsernameQuery(usersQuery)
                            .authoritiesByUsernameQuery(rolesQuery)
                            .dataSource(dataSource)
                            .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                
        http
            .authorizeRequests()
                    .antMatchers("/", "/index").permitAll()
                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .antMatchers("/alumno/**").hasAnyAuthority("ADMIN","USER")
                    .anyRequest().authenticated()
                .and()
                    .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and()
                    .formLogin()
                    .usernameParameter("email").passwordParameter("password")
                    .permitAll();
                    
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception{
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            return bCryptPasswordEncoder;
    }
    
}
