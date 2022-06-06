package br.com.springboot.mongodb.controle;

import br.com.springboot.mongodb.dominio.Empresa;
import br.com.springboot.mongodb.dto.PaginacaoEmpresaDTO;
import br.com.springboot.mongodb.servico.EmpresaServico;
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

@RestController
@RequestMapping(value = "/empresa")
public class EmpresaControle extends ObjetoControle {

    @Autowired
    private EmpresaServico servico;

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> buscar(@PathVariable String id) {
        Empresa empresa = null;
        try {
            empresa = this.servico.buscar(id);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }
        return ResponseEntity.status(HttpStatus.OK).body(empresa);
    }

    @GetMapping
    public ResponseEntity<PaginacaoEmpresaDTO> buscarTodos(@RequestParam(required = false) String nome,@RequestParam(defaultValue = "0") Integer pagina) {
        PaginacaoEmpresaDTO paginacao = new PaginacaoEmpresaDTO();
        try {
            pagina = validaPagina(pagina);
            nome = validaNome(nome);
            List<Empresa> empresas = this.servico.buscarTodos(nome);
            PagedListHolder<Empresa> empresasPaginacao = new PagedListHolder<>(empresas);
            empresasPaginacao.setPageSize(this.QTD_POR_PAGINA);
            empresasPaginacao.setPage(pagina);
            empresas = empresasPaginacao.getPageList();
            paginacao.setEmpresas(empresas);
            paginacao.setNumeroPaginas(empresasPaginacao.getPageCount());
            paginacao.setPaginaAtual(pagina);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }
        return ResponseEntity.status(HttpStatus.OK).body(paginacao);
    }

    @PostMapping
    public ResponseEntity<Void> inserir(@RequestBody Empresa empresa) {
        try {
            this.servico.inserir(empresa);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> alterar(@RequestBody Empresa empresa) {
        try {
            this.servico.alterar(empresa);
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
