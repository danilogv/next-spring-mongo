package br.com.springboot.mongodb.servico;

import br.com.springboot.mongodb.dominio.Empresa;
import br.com.springboot.mongodb.dominio.Funcionario;
import br.com.springboot.mongodb.padrao_projeto.FacadeRepositorio;
import br.com.springboot.mongodb.utilitario.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpresaServico {

    @Autowired
    private FacadeRepositorio repositorio;

    @Transactional(isolation = Isolation.READ_COMMITTED,readOnly = true)
    public Empresa buscar(String id) {
        this.empresaInexistente(id);
        Empresa empresa = this.repositorio.empresa.findById(id).get();
        return empresa;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,readOnly = true)
    public List<Empresa> buscarTodos(String nome) {
        List<Empresa> empresas;

        if (nome == null || nome.isEmpty()) {
            empresas = this.repositorio.empresa.findAllByOrderByNomeAsc();

            empresas = empresas
                    .stream()
                    .sorted(Comparator.comparing(Empresa::getNome,String.CASE_INSENSITIVE_ORDER))
                    .collect(Collectors.toList())
            ;
        }
        else
            empresas = this.repositorio.empresa.findByNomeLikeIgnoreCase(nome);

        return empresas;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void inserir(Empresa empresa) {
        this.validaEmpresa(empresa,true);
        this.repositorio.empresa.save(empresa);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void alterar(Empresa empresa) {
        this.validaEmpresa(empresa,false);
        this.repositorio.empresa.save(empresa);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void remover(String id) {
        this.empresaInexistente(id);
        Optional<Empresa> empresa = this.repositorio.empresa.findById(id);

        if (empresa.isPresent()) {
            List<Funcionario> funcionarios = empresa.get().getFuncionarios();
            List<String> idsFuncionarios = funcionarios.stream().map(Funcionario::getId).collect(Collectors.toList());
            this.repositorio.funcionario.deleteByIdIn(idsFuncionarios);
        }

        this.repositorio.empresa.deleteById(id);
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

        Boolean existeEmpresa = this.repositorio.empresa.existsByCnpj(empresa.getCnpj());

        if (existeEmpresa) {
            String msg = "Empresa com CNPJ já cadastrado.";
            if (ehInsercao) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
            }
            else if (!this.buscar(empresa.getId()).getCnpj().equals(empresa.getCnpj())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
            }
        }

    }

    private void empresaInexistente(String id) {
        Boolean existeEmpresa = this.repositorio.empresa.existsById(id);

        if (!existeEmpresa) {
            String msg = "Empresa não existe na base de dados.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
    }

}
