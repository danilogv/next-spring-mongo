package br.com.springboot.mongodb.excecao;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import java.io.Serializable;

@ControllerAdvice
public class Excecao implements Serializable {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> formataErro(ResponseStatusException excecao) {
        return new ResponseEntity<>(excecao.getReason(),excecao.getStatus());
    }

}
