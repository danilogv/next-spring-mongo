package br.com.springboot.mongodb.servico;

import br.com.springboot.mongodb.dominio.Empresa;
import br.com.springboot.mongodb.dominio.Funcionario;
import br.com.springboot.mongodb.repositorio.EmpresaRepositorio;
import br.com.springboot.mongodb.repositorio.FuncionarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioServico {

    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;

    @Transactional(isolation = Isolation.READ_COMMITTED,readOnly = true)
    public Funcionario buscar(String id) {
        Optional<Funcionario> funcionario = this.funcionarioRepositorio.findById(id);
        return funcionario.orElse(null);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,readOnly = true)
    public List<Funcionario> buscarTodos() {
        List<Funcionario> funcionarios = this.funcionarioRepositorio.findAllByOrderByNomeAsc();
        return funcionarios;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void inserir(Funcionario funcionario) {
        String empresaId = funcionario.getEmpresa().getId();
        Optional<Empresa> empresa = this.empresaRepositorio.findById(empresaId);
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
        /*String empresaId = funcionario.getEmpresa().getId();
        Optional<Empresa> empresa = this.empresaRepositorio.findById(empresaId);
        if (empresa.isPresent()) {
            List<Funcionario> funcionarios = empresa.get().getFuncionarios();

            Optional<Funcionario> func = funcionarios
                    .stream()
                    .filter(f -> f.getEmpresa().getId().equals(empresaId))
                    .findFirst()
            ;
            if (func.isPresent()) {
                funcionario.setEmpresa(func.get().getEmpresa());
                funcionarios.removeIf(f -> f.getEmpresa().getId().equals(empresaId));
                empresa.get().setFuncionarios(funcionarios);
                this.empresaRepositorio.save(empresa.get());
            }
        }

        this.funcionarioRepositorio.save(funcionario);*/
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void remover(String id) {

    }

}
