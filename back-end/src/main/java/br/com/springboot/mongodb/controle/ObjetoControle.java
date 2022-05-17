package br.com.springboot.mongodb.controle;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ObjetoControle {

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
