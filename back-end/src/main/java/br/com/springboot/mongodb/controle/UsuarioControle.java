package br.com.springboot.mongodb.controle;

import br.com.springboot.mongodb.dominio.Usuario;
import br.com.springboot.mongodb.servico.UsuarioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioControle extends ObjetoControle {

    @Autowired
    private UsuarioServico servico;

    @PostMapping
    public ResponseEntity<Void> inserir(@RequestBody Usuario usuario) {
        try {
            this.servico.inserir(usuario);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
