package br.com.springboot.mongodb.servico;

import br.com.springboot.mongodb.dominio.Empresa;
import br.com.springboot.mongodb.repositorio.EmpresaRepositorio;
import br.com.springboot.mongodb.utilitario.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class EmpresaServico {

    @Autowired
    private EmpresaRepositorio repositorio;

    @Transactional(isolation = Isolation.READ_COMMITTED,readOnly = true)
    public Empresa buscar(String id) {
        this.empresaInexistente(id);
        Empresa empresa = this.repositorio.findById(id).get();
        return empresa;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,readOnly = true)
    public List<Empresa> buscarTodos() {
        List<Empresa> alunos = this.repositorio.findAll();
        if (alunos.size() == 0) {
            String msg = "Não existem empresas cadastradas.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
        return alunos;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void inserir(Empresa empresa) {
        this.validaEmpresa(empresa,true);
        this.repositorio.save(empresa);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void alterar(Empresa empresa) {
        this.validaEmpresa(empresa,false);
        this.repositorio.save(empresa);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void remover(String id) {
        this.empresaInexistente(id);
        this.repositorio.deleteById(id);
    }

    private void validaEmpresa(Empresa empresa,Boolean ehInsercao) {
        if (!ehInsercao)
            this.empresaInexistente(empresa.getId());
        if (empresa.getNome() == null || empresa.getNome().isEmpty()) {
            String msg = "Informe o nome da empresa.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
        if (empresa.getCnpj() == null || empresa.getCnpj().isEmpty()) {
            String msg = "Informe o CNPJ da empresa.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
        if (!Util.cnpjValido(empresa.getCnpj())) {
            String msg = "CNPJ inválido.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
        if (ehInsercao && this.repositorio.existsByCnpj(empresa.getCnpj())) {
            String msg = "Empresa com CNPJ já cadastrado.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
        if (!ehInsercao && !this.buscar(empresa.getId()).getCnpj().equals(empresa.getCnpj()) && this.repositorio.existsByCnpj(empresa.getCnpj())) {
            String msg = "Empresa com CNPJ já cadastrado.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
    }

    private void empresaInexistente(String id) {
        if (!this.repositorio.existsById(id)) {
            String msg = "Empresa não existe na base de dados.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
    }

}
