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
        List<Empresa> empresas = this.empresaRepositorio.findAll();
        List<Funcionario> funcionariosEmpresaNova = new ArrayList<>();

        empresas.forEach(empresa -> {
            List<Funcionario> funcionarios = empresa.getFuncionarios();
            funcionarios.forEach(func -> {
                if (func.getId().equals(funcionario.getId())) {
                    funcionariosEmpresaNova.add(func);
                }
                else {
                    Empresa empresaAntiga = new Empresa();
                    empresaAntiga.setId(empresa.getId());
                    Optional<Empresa> empresaAntigaAtualizada = this.empresaRepositorio.findById(empresa.getId());
                    if (empresaAntigaAtualizada.isPresent()) {
                        List<Funcionario> funcionariosEmpresaAntiga = empresaAntigaAtualizada.get().getFuncionarios();
                        Optional<Funcionario> funcionarioAntigo = funcionariosEmpresaAntiga
                                .stream()
                                .filter(f -> f.getId().equals(funcionario.getId()))
                                .findFirst()
                        ;
                        if (funcionarioAntigo.isPresent()) {
                            funcionariosEmpresaAntiga.remove(funcionarioAntigo.get());
                            empresaAntigaAtualizada.get().setFuncionarios(funcionariosEmpresaAntiga);
                            this.empresaRepositorio.save(empresaAntigaAtualizada.get());
                        }
                    }
                }
            });
        });

        Optional<Empresa> empresaNovaAtualizada = this.empresaRepositorio.findById(funcionario.getEmpresa().getId());
        if (empresaNovaAtualizada.isPresent()) {
            empresaNovaAtualizada.get().setFuncionarios(funcionariosEmpresaNova);
            this.empresaRepositorio.save(empresaNovaAtualizada.get());
        }

        this.funcionarioRepositorio.save(funcionario);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void remover(String id) {
        Optional<Funcionario> funcionario = this.funcionarioRepositorio.findById(id);

        if (funcionario.isPresent()) {
            String empresaId = funcionario.get().getEmpresa().getId();
            List<Empresa> empresas = this.empresaRepositorio.findAll();
            Optional<Empresa> empresa = empresas
                    .stream()
                    .filter(emp -> emp.getId().equals(empresaId))
                    .findFirst()
            ;

            if (empresa.isPresent()) {
                List<Funcionario> funcionarios = empresa.get().getFuncionarios();
                funcionarios.removeIf(f -> f.getId().equals(id));
                empresa.get().setFuncionarios(funcionarios);
                this.empresaRepositorio.save(empresa.get());
            }
        }

        this.funcionarioRepositorio.deleteById(id);
    }

}
