package br.com.springboot.mongodb.controle;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class ObjetoControle {

    protected final Integer QTD_POR_PAGINA = 2;

    protected Integer validaPagina(Integer pagina) {
        if (pagina < 0)
            pagina = 0;
        return pagina;
    }

    protected String validaNome(String nome) {
        if (nome == null)
            nome = "";
        return nome;
    }

    public void geraExcecao(Exception ex) {
        if (ex instanceof ResponseStatusException) {
            HttpStatus status = ((ResponseStatusException) ex).getStatus();
            String msg = ((ResponseStatusException) ex).getReason();
            throw new ResponseStatusException(status,msg);
        }
        else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Erro de servidor");
        }
    }

}
