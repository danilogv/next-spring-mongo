package br.com.springboot.mongodb.servico;

import br.com.springboot.mongodb.dominio.Empresa;
import br.com.springboot.mongodb.dominio.Funcionario;
import br.com.springboot.mongodb.repositorio.EmpresaRepositorio;
import br.com.springboot.mongodb.repositorio.FuncionarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioServico {

    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;

    @Transactional(isolation = Isolation.READ_COMMITTED,readOnly = true)
    public Funcionario buscar(String empresaId) {
        return null;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,readOnly = true)
    public List<Funcionario> buscarTodos() {
        return new ArrayList<>();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void inserir(Funcionario funcionario) {
        Optional<Empresa> empresa = this.empresaRepositorio.findById(funcionario.getEmpresa().getId());
        if (empresa.isPresent()) {
            List<Funcionario> funcionarios = empresa.get().getFuncionarios();
            funcionarios.add(funcionario);
            empresa.get().setFuncionarios(funcionarios);
            this.funcionarioRepositorio.save(funcionario);
            this.empresaRepositorio.save(empresa.get());
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void alterar(Funcionario funcionario) {

    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void remover(String id) {

    }

}
