package br.com.springboot.mongodb.controle;

import br.com.springboot.mongodb.dto.FuncionarioDTO;
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
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/funcionario")
public class FuncionarioControle extends ObjetoControle {

    @Autowired
    private FuncionarioServico servico;

    @GetMapping("/{empresaId}")
    public ResponseEntity<FuncionarioDTO> buscar(@PathVariable String empresaId,@RequestBody FuncionarioDTO funcionario) {
        try {
            funcionario = this.servico.buscar(empresaId,funcionario);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }
        return ResponseEntity.status(HttpStatus.OK).body(funcionario);
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> buscarTodos(@RequestBody FuncionarioDTO funcionario) {
        List<FuncionarioDTO> funcionarios = new ArrayList<>();
        try {
            funcionarios = this.servico.buscarTodos(funcionario);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }
        return ResponseEntity.status(HttpStatus.OK).body(funcionarios);
    }

    @PostMapping
    public ResponseEntity<Void> inserir(@RequestBody FuncionarioDTO funcionario) {
        try {
            this.servico.inserir(funcionario);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> alterar(@RequestBody FuncionarioDTO funcionario) {
        try {
            this.servico.alterar(funcionario);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{empresaId}")
    public ResponseEntity<Void> excluir(@PathVariable String empresaId,@RequestBody FuncionarioDTO funcionario) {
        try {
            this.servico.remover(empresaId,funcionario);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}