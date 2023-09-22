package com.luxfacta.vernyy.api.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.luxfacta.vernyy.api.constants.Constantes;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {

    @Autowired
    private Environment env;
    
    

    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().configurationSource(corsConfigurationSource()).and()
                .csrf().disable()
                .authorizeHttpRequests()
                // Swagger UI
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-resources/**").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/webjars/**").permitAll()
                // Sistema
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/primeiroAcesso").permitAll()
                .requestMatchers(HttpMethod.POST, "/definirSenha").permitAll()
                .requestMatchers(HttpMethod.POST, "/loginMorador").permitAll()
                .requestMatchers(HttpMethod.POST, "/loginFuncionario").permitAll()
                .requestMatchers(HttpMethod.POST, "/refresh").permitAll()
                .requestMatchers(HttpMethod.POST, "/refreshMorador").permitAll()
                .requestMatchers(HttpMethod.POST, "/forgot").permitAll()
                .requestMatchers(HttpMethod.GET, "/forgotMorador/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/redefinirSenha/**").permitAll()
                
                .requestMatchers(HttpMethod.POST, "/reset/**").permitAll()
                .requestMatchers("/publico/**").permitAll()
                .requestMatchers("/sync/**").permitAll()
                
                .requestMatchers(HttpMethod.POST, "/updatePassword").hasAnyAuthority(Constantes.ROLE_CENTRAL,  Constantes.ROLE_ADMIN)
                .requestMatchers("/morador/**").hasAnyAuthority(Constantes.ROLE_MORADOR)
                .requestMatchers("/funcionario/**").hasAnyAuthority(Constantes.ROLE_FUNCIONARIO)
                .requestMatchers("/admin/**").hasAuthority(Constantes.ROLE_ADMIN)
                .requestMatchers("/central/**").hasAuthority(Constantes.ROLE_CENTRAL)
                .requestMatchers("/system/**").hasAnyAuthority(Constantes.ROLE_CENTRAL,Constantes.ROLE_ADMIN, Constantes.ROLE_MORADOR)
                
                .anyRequest().authenticated()
                .and()
                // filtra outras requisições para verificar a presença do JWT no header
                .addFilterBefore( new JWTAuthenticationFilter(env),
                    UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }           
                
    
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        
        CorsConfiguration configAdmin = new CorsConfiguration().applyPermitDefaultValues();
        configAdmin.setAllowCredentials(true);
        configAdmin.addAllowedHeader("X-Requested-With");
        configAdmin.addAllowedHeader("Origin");
        configAdmin.addAllowedHeader("Content-Type");
        configAdmin.addAllowedHeader("Accept");
        configAdmin.addAllowedHeader("Authorization");
        configAdmin.addAllowedHeader("Refresh");
        configAdmin.addAllowedHeader("Access-Control-Allow-Origin");
        configAdmin.addAllowedHeader("Access-Control-Request-Method");
        configAdmin.addAllowedHeader("Access-Control-Request-Headers");
        
        configAdmin.addExposedHeader("Authorization");
        configAdmin.addExposedHeader("Content-Type");
        configAdmin.addExposedHeader("Access-Control-Allow-Origin");
        configAdmin.addExposedHeader("Access-Control-Allow-Headers");
        configAdmin.addExposedHeader("Access-Control-Request-Method");
        configAdmin.addExposedHeader("Access-Control-Request-Headers");
        configAdmin.addExposedHeader("Refresh");
        configAdmin.addExposedHeader("Allow");
        
        configAdmin.addAllowedMethod(HttpMethod.PUT);
        configAdmin.addAllowedMethod(HttpMethod.DELETE);
        configAdmin.addAllowedMethod(HttpMethod.GET);
        configAdmin.addAllowedMethod(HttpMethod.POST);
        configAdmin.addAllowedMethod(HttpMethod.OPTIONS);
        configAdmin.addAllowedOriginPattern("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/central/**", configAdmin);
        source.registerCorsConfiguration("/morador/**", configAdmin);
        source.registerCorsConfiguration("/funcionario/**", configAdmin);
        source.registerCorsConfiguration("/admin/**", configAdmin);
        source.registerCorsConfiguration("/publico/**", configAdmin);
        source.registerCorsConfiguration("/sync/**", configAdmin);
        
        source.registerCorsConfiguration("/swagger-ui/**", configAdmin);
        source.registerCorsConfiguration("/login", configAdmin);
        source.registerCorsConfiguration("/updatePassword", configAdmin);
        source.registerCorsConfiguration("/reset/**", configAdmin);
        source.registerCorsConfiguration("/forgot", configAdmin);
        source.registerCorsConfiguration("/refresh", configAdmin);
        source.registerCorsConfiguration("/loginMorador", configAdmin);
        source.registerCorsConfiguration("/forgotMorador", configAdmin);
        source.registerCorsConfiguration("/refreshMorador", configAdmin);
        source.registerCorsConfiguration("/loginFuncionario", configAdmin);
        source.registerCorsConfiguration("/redefinirSenha", configAdmin);
        
        
       return source;
    }
    

    
    
}
