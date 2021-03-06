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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuncionarioServico {

    @Autowired
    private FacadeRepositorio repositorio;

    private final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    private final Integer IDADE_MINIMA = 18;

    @Transactional(isolation = Isolation.READ_COMMITTED,readOnly = true)
    public Funcionario buscar(String id) {
        this.funcionarioInexistente(id);
        Optional<Funcionario> funcionario = this.repositorio.funcionario.findById(id);
        return funcionario.orElse(null);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,readOnly = true)
    public List<Funcionario> buscarTodos(String nome) {
        List<Funcionario> funcionarios;

        if (nome == null || nome.isEmpty()) {
            funcionarios = this.repositorio.funcionario.findAllByOrderByNomeAsc();

            funcionarios = funcionarios
                    .stream()
                    .sorted(Comparator.comparing(Funcionario::getNome,String.CASE_INSENSITIVE_ORDER))
                    .collect(Collectors.toList())
            ;
        }
        else
            funcionarios = this.repositorio.funcionario.findByNomeLikeIgnoreCase(nome);

        return funcionarios;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void inserir(Funcionario funcionario) {
        this.validaFuncionario(funcionario,true);
        String empresaId = funcionario.getEmpresa().getId();
        Optional<Empresa> empresa = this.repositorio.empresa.findById(empresaId);

        if (empresa.isPresent()) {
            List<Funcionario> funcionarios = empresa.get().getFuncionarios();
            funcionarios.add(funcionario);
            empresa.get().setFuncionarios(funcionarios);
            this.repositorio.funcionario.save(funcionario);
            this.repositorio.empresa.save(empresa.get());
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void alterar(Funcionario funcionario) {
        this.validaFuncionario(funcionario,false);
        List<Empresa> empresas = this.repositorio.empresa.findAll();
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
                    Optional<Empresa> empresaAntigaAtualizada = this.repositorio.empresa.findById(empresa.getId());
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
                            this.repositorio.empresa.save(empresaAntigaAtualizada.get());
                        }
                    }
                }
            });
        });

        Optional<Empresa> empresaNovaAtualizada = this.repositorio.empresa.findById(funcionario.getEmpresa().getId());
        if (empresaNovaAtualizada.isPresent()) {
            empresaNovaAtualizada.get().setFuncionarios(funcionariosEmpresaNova);
            this.repositorio.empresa.save(empresaNovaAtualizada.get());
        }

        this.repositorio.funcionario.save(funcionario);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void remover(String id) {
        this.funcionarioInexistente(id);
        Optional<Funcionario> funcionario = this.repositorio.funcionario.findById(id);

        if (funcionario.isPresent()) {
            String empresaId = funcionario.get().getEmpresa().getId();
            List<Empresa> empresas = this.repositorio.empresa.findAll();
            Optional<Empresa> empresa = empresas
                    .stream()
                    .filter(emp -> emp.getId().equals(empresaId))
                    .findFirst()
            ;

            if (empresa.isPresent()) {
                List<Funcionario> funcionarios = empresa.get().getFuncionarios();
                funcionarios.removeIf(f -> f.getId().equals(id));
                empresa.get().setFuncionarios(funcionarios);
                this.repositorio.empresa.save(empresa.get());
            }
        }

        this.repositorio.funcionario.deleteById(id);
    }

    private void validaFuncionario(Funcionario funcionario,Boolean ehInsercao) {
        if (!ehInsercao)
            this.funcionarioInexistente(funcionario.getId());

        if (funcionario.getNome() == null || funcionario.getNome().isEmpty()) {
            String msg = "Informe o NOME do funcion??rio.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        if (funcionario.getCpf() == null || funcionario.getCpf().isEmpty()) {
            String msg = "Informe o CPF da empresa.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        if (funcionario.getSalario() == null || funcionario.getSalario().compareTo(this.SALARIO_MINIMO) < 0) {
            String msg = "SAL??RIO do funcion??rio deve ser maior que o sal??rio m??nimo atual.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        if (funcionario.getIdade() == null || funcionario.getIdade().compareTo(this.IDADE_MINIMA) < 0) {
            String msg = "IDADE do funcion??rio deve ser maior que 18 anos.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        if (funcionario.getDataDesligamento() != null && funcionario.getDataDesligamento().isAfter(LocalDate.now())) {
            String msg = "DATA DE DESLIGAMENTO deve ser menor ou igual ?? data atual.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        if (!Util.cpfValido(funcionario.getCpf())) {
            String msg = "CPF inv??lido.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        Boolean existeFuncionario = this.repositorio.funcionario.existsByCpf(funcionario.getCpf());

        if (existeFuncionario) {
            String msg = "Funcion??rio j?? cadastrado com esse CPF nessa ou em outra empresa.";
            if (ehInsercao) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
            }
            else if (!this.buscar(funcionario.getId()).getCpf().equals(funcionario.getCpf())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
            }
        }

    }

    private void funcionarioInexistente(String id) {
        Boolean existeFuncionario = this.repositorio.funcionario.existsById(id);

        if (!existeFuncionario) {
            String msg = "Funcion??rio n??o existe na base de dados.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
    }

}
