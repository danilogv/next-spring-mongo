package br.com.springboot.mongodb.configuracao;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AutenticacaoRequisicao implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest requisicao,HttpServletResponse resposta,AuthenticationException excecao) throws IOException {
        resposta.sendError(HttpServletResponse.SC_UNAUTHORIZED,excecao.getMessage());
    }

}
