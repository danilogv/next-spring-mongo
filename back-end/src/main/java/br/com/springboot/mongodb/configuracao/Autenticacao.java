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
    protected void doFilterInternal(HttpServletRequest requisicao,HttpServletResponse resposta,FilterChain filtro) throws ServletException, IOException {
        String token = this.jwt.obtemToken(requisicao);

        if (Objects.nonNull(token)) {
            String nomeUsuario = this.jwt.obtemLoginDoToken(token);

            if (Objects.nonNull(nomeUsuario)) {
                UserDetails usuario = this.usuario.loadUserByUsername(nomeUsuario);

                if (this.jwt.validaToken(token,usuario)) {
                    UsernamePasswordAuthenticationToken autenticacao = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
                    autenticacao.setDetails(new WebAuthenticationDetails(requisicao));
                    SecurityContextHolder.getContext().setAuthentication(autenticacao);
                }
            }

        }

        filtro.doFilter(requisicao,resposta);
    }

}
