package com.chhinghor.hrd.chhinghorjwt.configuration;

import com.chhinghor.hrd.chhinghorjwt.security.jwt.EntryPointJwt;
import com.chhinghor.hrd.chhinghorjwt.security.jwt.JwtTokenFilter;
import com.chhinghor.hrd.chhinghorjwt.security.jwt.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    public EntryPointJwt unauthorizedHandler;

    @Autowired
    public UserDetailsServiceImp userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Order(1)
    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter{
        @Autowired
        public PasswordEncoder passwordEncoder;

        @Bean
        public AuthenticationManager authenticationManager() throws Exception{
            return super.authenticationManager();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            //  super.configure(auth);
            auth
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder);
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            super.configure(web);
            web.ignoring().antMatchers("/v2/api-docs",
                    "/swagger-ui/**", " /v3/api-docs/**",
                    "/swagger-ui.html", "/resources/**", "/static/**", "/css/**", "/js/**", "/images/**",
                    "/resources/static/**", "/css/**", "/js/**", "/img/**", "/fonts/**",
                    "/images/**", "/scss/**", "/vendor/**", "/favicon.ico", "/auth/**", "/favicon.png",
                    "/v2/api-docs", "/configuration/ui", "/configuration/security",
                    "/webjars/**", "/swagger-resources/**", "/actuator", "/swagger-ui/**",
                    "/actuator/**", "/swagger-ui/index.html", "/swagger-ui/");
        }

        @Bean
        public JwtTokenFilter jwtTokenFilter(){
            return  new JwtTokenFilter();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // super.configure(http);
            http
                    .cors().configurationSource(corsConfigurationSource()).and()
                    .csrf().disable()
//                    .exceptionHandling()
//                    .authenticationEntryPoint(unauthorizedHandler)
//                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .antMatcher("/api/**")
                    .authorizeRequests()
//                    .antMatchers("/api/v1/auth/**").permitAll()
                    .antMatchers( "/api/v1/auth/**", "/api/v1/auth/sign-up",
                            "/api/v1/users/{id}", "/api/v1/users", "/api/v1/users/{id}")
                    .permitAll()
//                    .antMatchers(  "/api/v1/users/{id}", "/api/v1/users", "/api/v1/users/{id}").access("hasAuthority('ROLE_ADMIN')")

                    .antMatchers("/api/**").permitAll()
                    .antMatchers("/api/v3/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated() ;
            http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Collections.singletonList("*"));
            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
            return source;
        }
    }


}

