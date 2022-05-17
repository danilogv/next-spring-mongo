package br.com.springboot.mongodb.excecao;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serializable;

@ControllerAdvice
public class Excecao implements Serializable {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Erro> formataErro(ResponseStatusException excecao) {
        Erro erro = new Erro();
        erro.setStatus(excecao.getStatus().value());
        erro.setMensagem(excecao.getReason());
        return ResponseEntity.status(excecao.getStatus()).body(erro);
    }

}
