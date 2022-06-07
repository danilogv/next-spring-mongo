package br.com.springboot.mongodb.controle;

import br.com.springboot.mongodb.dominio.Funcionario;
import br.com.springboot.mongodb.dto.PaginacaoFuncionarioDTO;
import br.com.springboot.mongodb.servico.FuncionarioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public ResponseEntity<PaginacaoFuncionarioDTO> buscarTodos(@RequestParam Map<String,String> parametros) {
        PaginacaoFuncionarioDTO paginacao = new PaginacaoFuncionarioDTO();
        try {
            String nome = null;
            Integer pagina = 0;

            if (Objects.nonNull(parametros)) {
                if (Objects.nonNull(parametros.get("pagina"))) {
                    nome = parametros.get("nome");
                }

                if (Objects.nonNull(parametros.get("pagina"))) {
                    pagina = Integer.parseInt(parametros.get("pagina"));
                }
            }

            pagina = validaPagina(pagina);
            nome = validaNome(nome);
            List<Funcionario> funcionarios = this.servico.buscarTodos(nome);
            PagedListHolder<Funcionario> funcionariosPaginacao = new PagedListHolder<>(funcionarios);
            funcionariosPaginacao.setPageSize(this.QTD_POR_PAGINA);
            funcionariosPaginacao.setPage(pagina);
            funcionarios = funcionariosPaginacao.getPageList();
            paginacao.setFuncionarios(funcionarios);
            paginacao.setNumeroPaginas(funcionariosPaginacao.getPageCount());
            paginacao.setPaginaAtual(pagina);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }
        return ResponseEntity.status(HttpStatus.OK).body(paginacao);
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
