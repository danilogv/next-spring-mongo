package br.com.springboot.mongodb.repositorio;

import br.com.springboot.mongodb.dominio.Funcionario;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface FuncionarioRepositorio extends MongoRepository<Funcionario,String> {

    List<Funcionario> findAllByOrderByNomeAsc();

}
