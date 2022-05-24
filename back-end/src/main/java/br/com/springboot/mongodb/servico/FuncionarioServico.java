package br.com.springboot.mongodb.servico;

import br.com.springboot.mongodb.dominio.Empresa;
import br.com.springboot.mongodb.dto.FuncionarioDTO;
import br.com.springboot.mongodb.repositorio.EmpresaRepositorio;
import br.com.springboot.mongodb.utilitario.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuncionarioServico {

    @Autowired
    private EmpresaRepositorio repositorio;

    @Transactional(isolation = Isolation.READ_COMMITTED,readOnly = true)
    public FuncionarioDTO buscar(String empresaId) {
        /*this.empresaNaoInfomada(empresaId);
        Optional<Empresa> empresa = this.repositorio.findById(empresaId);
        this.empresaNaoCadastrada(empresa);
        List<FuncionarioDTO> funcionarios = empresa.get().getFuncionarios();
        Optional<FuncionarioDTO> func = funcionarios
                .stream()
                .filter(f -> f.getCpf().equals(funcionario.getCpf()))
                .findFirst()
        ;
        if (func.isEmpty()) {
            String msg = "Funcionário não cadastrado.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
        return func.get();*/
        return null;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,readOnly = true)
    public List<FuncionarioDTO> buscarTodos() {
        List<Empresa> empresas = this.repositorio.findAll();
        var objeto = new Object() {
            List<FuncionarioDTO> funcionarios = new ArrayList<>();
        };
        empresas.forEach(empresa -> {
            if (empresa.getFuncionarios() != null && empresa.getFuncionarios().size() > 0) {
                objeto.funcionarios.addAll(empresa.getFuncionarios());
            }
        });
        objeto.funcionarios = objeto.funcionarios
                .stream()
                .sorted(Comparator.comparing(FuncionarioDTO::getNome))
                .collect(Collectors.toList())
        ;
        return objeto.funcionarios;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void inserir(FuncionarioDTO funcionario) {
        this.empresaNaoInfomada(funcionario.getEmpresaId());
        Optional<Empresa> empresa = this.repositorio.findById(funcionario.getEmpresaId());
        this.validaFuncionario(funcionario,empresa,true);
        List<FuncionarioDTO> funcionarios = empresa.get().getFuncionarios();
        funcionarios.add(funcionario);
        empresa.get().setFuncionarios(funcionarios);
        this.repositorio.save(empresa.get());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void alterar(FuncionarioDTO funcionario) {
        this.empresaNaoInfomada(funcionario.getEmpresaId());
        Optional<Empresa> empresa = this.repositorio.findById(funcionario.getEmpresaId());
        this.validaFuncionario(funcionario,empresa,false);
        List<FuncionarioDTO> funcionarios = empresa.get().getFuncionarios();
        List<FuncionarioDTO> funcionariosLista = new ArrayList<>();
        funcionarios.forEach(func -> {
            if (func.getCpf().equals(funcionario.getCpf())) {
                funcionariosLista.add(funcionario);
            }
            else {
                funcionariosLista.add(func);
            }
        });
        empresa.get().setFuncionarios(funcionariosLista);
        this.repositorio.save(empresa.get());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void remover(String empresaid,FuncionarioDTO funcionario) {
        this.empresaNaoInfomada(funcionario.getEmpresaId());
        Optional<Empresa> empresa = this.repositorio.findById(empresaid);
        this.validaFuncionario(funcionario,empresa,false);
        List<FuncionarioDTO> funcionarios = empresa.get().getFuncionarios();
        funcionarios.removeIf(f -> f.getCpf().equals(funcionario.getCpf()));
        empresa.get().setFuncionarios(funcionarios);
        this.repositorio.save(empresa.get());
    }

    private void validaFuncionario(FuncionarioDTO funcionario,Optional<Empresa> empresa,Boolean ehInsercao) {
        this.empresaNaoCadastrada(empresa);
        if (!Util.cpfValido(funcionario.getCpf())) {
            String msg = "CPF inválido.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
        List<FuncionarioDTO> funcionarios = empresa.get().getFuncionarios();
        if (ehInsercao) {
            funcionarios.forEach(func -> {
                String cpf = func.getCpf();
                if (funcionario.getCpf().equals(cpf)) {
                    String msg = "Funcionário com esse CPF já cadastrado na empresa.";
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
                }
            });
        }
        else {
            long qtdFuncionarios = funcionarios.stream().filter(f -> f.getCpf().equals(funcionario.getCpf())).count();
            if (qtdFuncionarios == 0) {
                String msg = "Funcionário não cadastrado.";
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
            }
        }
    }

    private void empresaNaoInfomada(String empresaId) {
        if (empresaId == null) {
            String msg = "Informe a EMPRESA.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
    }

    private void empresaNaoCadastrada(Optional<Empresa> empresa) {
        if (empresa.isEmpty()) {
            String msg = "Empresa não cadastrada.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
    }

}
