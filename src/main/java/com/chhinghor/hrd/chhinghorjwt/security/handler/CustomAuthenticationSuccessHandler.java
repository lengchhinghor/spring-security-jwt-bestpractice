package com.chhinghor.hrd.chhinghorjwt.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_OK);
        for (GrantedAuthority auth:authentication.getAuthorities()){
//            if (ERole.TEAM.name().equals(auth.getAuthority())){
                System.out.println("Here getAuthorities: "+auth.getAuthority());
                response.sendRedirect("/");
                return;
//            }
        }

        String attemptedURI =(String)  request.getSession().getAttribute("ATTEMPTED_URI");
        if (StringUtils.hasText(attemptedURI)){
            response.sendRedirect(attemptedURI);
            return;
        }
        System.out.println("Authority value : "+ authentication.getAuthorities());
        response.sendRedirect("/login");
    }


}