package br.com.springboot.mongodb.repositorio;

import br.com.springboot.mongodb.dominio.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepositorio extends MongoRepository<Usuario,String> {

    Boolean existsByEmail(String email);
    Usuario findByEmail(String email);

}
