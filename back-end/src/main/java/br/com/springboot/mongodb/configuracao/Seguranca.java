package br.com.springboot.mongodb.configuracao;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Configuration
@EnableWebSecurity
public class Seguranca extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        cors.setAllowedOrigins(List.of("*"));
        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
        cors.setExposedHeaders(List.of("Authorization"));
        http
            .authorizeRequests().antMatchers("/**").permitAll().anyRequest()
            .authenticated().and().csrf().disable().cors().configurationSource(request -> cors)
        ;
    }

}

