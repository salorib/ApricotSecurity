/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apricot.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author Argos
 */
@Component
public class ApricotAccessDeniedHandler implements AccessDeniedHandler{
        private static Logger logger = LoggerFactory.getLogger(ApricotAccessDeniedHandler.class);
    
    @Override
    public void handle(HttpServletRequest hsr, 
            HttpServletResponse hsrps, 
            AccessDeniedException ade) 
            throws IOException, ServletException {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if(auth != null){
            logger.info("user '" + auth.getName()
            + "' intento accesar a la URL: "
            + hsr.getRequestURI());
        }
        
        hsrps.sendRedirect(hsr.getContextPath() + "/403");
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
