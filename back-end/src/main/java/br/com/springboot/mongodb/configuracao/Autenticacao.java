package br.com.springboot.mongodb.configuracao;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class Autenticacao extends OncePerRequestFilter {

    private UsuarioConfiguracao usuario;

    private JWT jwt;

    public Autenticacao(UsuarioConfiguracao usuario,JWT jwt) {
        this.usuario = usuario;
        this.jwt = jwt;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException, IOException {
        String authToken = this.jwt.getToken(request);

        if (Objects.nonNull(authToken)) {
            String username = this.jwt.getUsernameFromToken(authToken);

            if (Objects.nonNull(username)) {
                UserDetails usuario = this.usuario.loadUserByUsername(username);

                if (this.jwt.validateToken(authToken,usuario)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        }

        filterChain.doFilter(request,response);
    }

}
