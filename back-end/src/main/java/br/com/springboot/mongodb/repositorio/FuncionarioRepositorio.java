package br.com.springboot.mongodb.repositorio;

import br.com.springboot.mongodb.dominio.Funcionario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FuncionarioRepositorio extends MongoRepository<Funcionario,String> {
}
