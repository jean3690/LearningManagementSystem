package org.lms.config;

import org.lms.security.Filter.CustomAfterFilter;
import org.lms.security.Filter.JwtAuthorizeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private JwtAuthorizeFilter jwtAuthorizeFilter;
    private CustomAfterFilter customAfterFilter;

    public SecurityConfig(JwtAuthorizeFilter jwtAuthorizeFilter, CustomAfterFilter customAfterFilter) {
        this.jwtAuthorizeFilter = jwtAuthorizeFilter;
        this.customAfterFilter = customAfterFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(CsrfConfigurer::disable)
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/**").permitAll()
                            .anyRequest().authenticated();
                }).addFilterBefore(jwtAuthorizeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(customAfterFilter,UsernamePasswordAuthenticationFilter.class).build();
    }
}
