package br.com.springboot.mongodb.controle;

import br.com.springboot.mongodb.dominio.Funcionario;
import br.com.springboot.mongodb.servico.FuncionarioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/funcionario")
public class FuncionarioControle extends ObjetoControle {

    @Autowired
    private FuncionarioServico servico;

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscar(@PathVariable String id) {
        Funcionario funcionario = null;
        try {
            funcionario = this.servico.buscar(id);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }
        return ResponseEntity.status(HttpStatus.OK).body(funcionario);
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> buscarTodos(@RequestParam(required = false) String nome) {
        List<Funcionario> funcionarios = new ArrayList<>();
        try {
            funcionarios = this.servico.buscarTodos(nome);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }
        return ResponseEntity.status(HttpStatus.OK).body(funcionarios);
    }

    @PostMapping
    public ResponseEntity<Void> inserir(@RequestBody Funcionario funcionario) {
        try {
            this.servico.inserir(funcionario);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> alterar(@RequestBody Funcionario funcionario) {
        try {
            this.servico.alterar(funcionario);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        try {
            this.servico.remover(id);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
