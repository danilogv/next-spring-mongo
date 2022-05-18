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
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioServico {

    @Autowired
    private EmpresaRepositorio repositorio;

    @Transactional(isolation = Isolation.READ_COMMITTED,readOnly = true)
    public FuncionarioDTO buscar(String empresaId) {
        return new FuncionarioDTO();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,readOnly = true)
    public List<FuncionarioDTO> buscarTodos() {
        return new ArrayList<>();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void inserir(FuncionarioDTO funcionario) {
        Optional<Empresa> empresa = this.repositorio.findById(funcionario.getEmpresaId());
        this.validaFuncionario(funcionario,empresa);
        List<FuncionarioDTO> funcionarios = empresa.get().getFuncionarios();
        funcionarios.add(funcionario);
        empresa.get().setFuncionarios(funcionarios);
        this.repositorio.save(empresa.get());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void alterar(FuncionarioDTO funcionario) {

    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void remover(String id) {

    }

    private void validaFuncionario(FuncionarioDTO funcionario,Optional<Empresa> empresa) {
        if (empresa.isEmpty()) {
            String msg = "Empresa não cadastrada.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
        if (!Util.cpfValido(funcionario.getCpf())) {
            String msg = "CPF inválido.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
        List<FuncionarioDTO> funcionarios = empresa.get().getFuncionarios();
        for (FuncionarioDTO funcionarioLista : funcionarios) {
            String cpf = funcionarioLista.getCpf();
            if (funcionario.getCpf().equals(cpf)) {
                String msg = "Funcionário com esse CPF já cadastrado na empresa.";
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
            }
        }
    }

}
