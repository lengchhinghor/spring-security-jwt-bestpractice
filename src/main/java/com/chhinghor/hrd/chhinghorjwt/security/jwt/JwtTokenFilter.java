package com.chhinghor.hrd.chhinghorjwt.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        try{

            String jwt = parseJwt(httpServletRequest);

            // System.out.println("Token from filter is : "+ jwt); // get the token from the request url

            UserDetailsImp userDetailsImp;

            if ( jwt != null && jwtUtils.validateJwtToken(jwt)){

                // this could have been get email
                String phoneNumber = jwtUtils.getPhoneNumberFromJwt(jwt);
                userDetailsImp = (UserDetailsImp) userDetailsServiceImp.loadUserByUsername(phoneNumber);
                // Use this to authenticate
                UsernamePasswordAuthenticationToken authentication  =
                        new UsernamePasswordAuthenticationToken(userDetailsImp, null, userDetailsImp.getAuthorities());


                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

//            if ( jwt != null && jwtUtils.validateJwtToken(jwt)){
//
//                // this could have been get email
//                String username = jwtUtils.getUserNameFromJwtToken(jwt);
//                userDetailsImp = (UserDetailsImp) userDetailsServiceImp.loadUserByUsername(username);
//                // Use this to authenticate
//                UsernamePasswordAuthenticationToken authentication  =
//                        new UsernamePasswordAuthenticationToken(userDetailsImp, null, userDetailsImp.getAuthorities());
//
//
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }

        }catch (Exception e){

            e.printStackTrace();
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }


    private String parseJwt( HttpServletRequest request){

        String header = request.getHeader("Authorization");
        String prefix = "Bearer ";

        if(StringUtils.hasText(header) && header.startsWith(prefix)){
            return header.substring(prefix.length()); // Take only the token. Verifying the process.

        }
        return  null;
    }
}